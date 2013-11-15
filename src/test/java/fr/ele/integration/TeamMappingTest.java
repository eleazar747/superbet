package fr.ele.integration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.annotation.Nullable;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import fr.ele.core.csv.CsvContext;
import fr.ele.core.csv.CsvMarshaller;
import fr.ele.core.csv.GraphResolver;
import fr.ele.core.matcher.EqualMatcher;
import fr.ele.core.matcher.IgnoreCaseMatcher;
import fr.ele.core.matcher.LevenshteinMatcher;
import fr.ele.core.matcher.SimilarMatcher;
import fr.ele.core.matcher.StringMatcher;
import fr.ele.core.matcher.noise.BlankRemover;
import fr.ele.core.matcher.noise.NoiseRemover;
import fr.ele.core.matcher.noise.NoiseTeamRemover;
import fr.ele.csv.SuperBetGraphResolver;
import fr.ele.model.DataMapping;
import fr.ele.model.QDataMapping;
import fr.ele.model.QUnMatchedPlayer;
import fr.ele.model.RefEntityType;
import fr.ele.model.UnMatchedPlayer;
import fr.ele.model.ref.BookMaker;
import fr.ele.services.mapping.BookMakerSynchronizerService;
import fr.ele.services.repositories.BookMakerRepository;
import fr.ele.services.repositories.DataMappingRepository;
import fr.ele.services.repositories.UnMatchedPlayerRepository;

public class TeamMappingTest extends AbstractSuperbetIntegrationTest {

	@Autowired
	private BookMakerRepository bookMakerRepository;

	@Autowired
	private DataMappingRepository dataMappingRepository;

	@Autowired
	private BookMakerSynchronizerService bookMakerSynchronizerService;

	@Autowired
	private UnMatchedPlayerRepository unMatchedPlayerRepository;

	@Override
	@Before
	public void initializeDatas() {
		super.initializeDatas();
	}

	@Test
	public void teamMatcher() throws Throwable {
		BookMaker bookMaker = bookMakerRepository.findByCode("williamhill");
		QDataMapping datamapping = QDataMapping.dataMapping;
		Iterable<DataMapping> mappings = dataMappingRepository
				.findAll(datamapping.bookMaker.eq(bookMaker).and(
						datamapping.refEntityType.eq(RefEntityType.TEAM)));
		// TODO : TO REMOVE WHEN DATA MAPPING FILE WILL BE CLEAN
		for (DataMapping mapping : mappings) {
			dataMappingRepository.delete(mapping);
		}
		bookMakerSynchronizerService.synchronize(bookMaker);
		Iterable<UnMatchedPlayer> iterable = unMatchedPlayerRepository
				.findAll(QUnMatchedPlayer.unMatchedPlayer.bookMaker
						.eq(bookMaker));

		// Pour l'instant on s'occupe du Foot
		List<UnMatchedPlayer> unmatched = Lists.newArrayList(Iterables.filter(
				iterable, new Predicate<UnMatchedPlayer>() {

					@Override
					public boolean apply(@Nullable UnMatchedPlayer input) {
						return input.getSport() != null
								&& "Football"
										.equals(input.getSport().getCode());
					}

				}));

		List<String> teams = initModelTeams();

		NoiseTeamRemover noiseTeamRemover = new NoiseTeamRemover();
		GraphResolver graphResolver = new SuperBetGraphResolver(
				repositoryRegistry);
		CsvContext<DataMapping> context = CsvContext.create(DataMapping.class,
				graphResolver);
		CsvMarshaller<DataMapping> marshaller = context.newMarshaller();

		Map<DataMapping, UnMatchedPlayer> matched = applyMatchingAlgorithm(
				bookMaker, unmatched, teams, new BlankRemover(),
				new EqualMatcher());
		System.out.println("equal " + matched.size() + "/" + unmatched.size());
		if (matched.size() > 0) {
			removeMatched(unmatched, matched);
			marshaller.marshall(Lists.newArrayList(matched.keySet()),
					System.out);
		}

		matched = applyMatchingAlgorithm(bookMaker, unmatched, teams,
				noiseTeamRemover, new IgnoreCaseMatcher());
		System.out.println("ignore case " + matched.size() + "/"
				+ unmatched.size());
		if (matched.size() > 0) {
			removeMatched(unmatched, matched);
			marshaller.marshall(Lists.newArrayList(matched.keySet()),
					System.out);
		}

		for (int i = 1; i <= 3; i++) {
			double treshold = 1d - i / 10d;
			matched = applyMatchingAlgorithm(bookMaker, unmatched, teams,
					noiseTeamRemover, new SimilarMatcher(treshold));
			System.out.println("similarity " + treshold * 100 + "% "
					+ matched.size() + "/" + unmatched.size());
			if (matched.size() > 0) {
				removeMatched(unmatched, matched);
				marshaller.marshall(Lists.newArrayList(matched.keySet()),
						System.out);
			}
		}
		for (int i = 1; i <= 3; i++) {
			matched = applyMatchingAlgorithm(bookMaker, unmatched, teams,
					noiseTeamRemover, new LevenshteinMatcher(i));
			System.out.println("levenshtein " + i + " " + matched.size() + "/"
					+ unmatched.size());
			if (matched.size() > 0) {
				removeMatched(unmatched, matched);
				marshaller.marshall(Lists.newArrayList(matched.keySet()),
						System.out);
			}
		}

		for (UnMatchedPlayer player : unmatched) {
			// on affiche le restant
			System.err.println(player.getCode());
		}
	}

	private void removeMatched(List<UnMatchedPlayer> unmatched,
			Map<DataMapping, UnMatchedPlayer> matched) {
		Collection<UnMatchedPlayer> toRemove = matched.values();
		Set<Long> ids = new HashSet<Long>(toRemove.size());
		for (UnMatchedPlayer player : toRemove) {
			ids.add(player.getId());
			unmatched.remove(player);
		}
		for (Long id : ids) {
			unMatchedPlayerRepository.delete(id);
		}
	}

	private Map<DataMapping, UnMatchedPlayer> applyMatchingAlgorithm(
			BookMaker bookMaker, List<UnMatchedPlayer> unmatched,
			List<String> teams, NoiseRemover noiseRemover, StringMatcher matcher) {
		Map<DataMapping, UnMatchedPlayer> matched = new HashMap<DataMapping, UnMatchedPlayer>();
		for (UnMatchedPlayer player : unmatched) {
			String match = match(bookMaker, teams, player, matcher,
					noiseRemover);
			if (match != null) {
				DataMapping mapping = new DataMapping();
				mapping.setBookMaker(bookMaker);
				mapping.setBookMakerCode(player.getCode());
				mapping.setModelCode(match);
				mapping.setRefEntityType(RefEntityType.TEAM);
				matched.put(mapping, player);
			}
		}

		return matched;
	}

	private List<String> initModelTeams() throws FileNotFoundException {
		List<String> teams = new ArrayList<String>(500);
		Scanner scanner = new Scanner(
				new File(
						"C:/Users/868012/Documents/Workspace/Workspace_eclipse/superbet/team.txt"));
		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			teams.add(line);
		}
		return teams;
	}

	private String match(BookMaker bookMaker, List<String> teams,
			UnMatchedPlayer player, StringMatcher matcher,
			NoiseRemover noiseRemover) {
		String cleanBookmaker = noiseRemover.removeNoise(player.getCode());
		for (String team : teams) {
			String cleanModel = noiseRemover.removeNoise(team);
			if (matcher.match(cleanBookmaker, cleanModel)) {
				return team;
			}
		}
		return null;
	}
}
