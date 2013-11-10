package fr.ele.integration;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;

import fr.ele.core.csv.CsvContext;
import fr.ele.core.csv.CsvMarshaller;
import fr.ele.core.csv.GraphResolver;
import fr.ele.core.matcher.IgnoreCaseMatcher;
import fr.ele.core.matcher.NoiseTeamRemover;
import fr.ele.core.matcher.SimilarMatcher;
import fr.ele.core.matcher.StringMatcher;
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
        BookMaker bookMaker = bookMakerRepository.findByCode("betclic");
        QDataMapping datamapping = QDataMapping.dataMapping;
        Iterable<DataMapping> mappings = dataMappingRepository
                .findAll(datamapping.bookMaker.eq(bookMaker).and(
                        datamapping.refEntityType.eq(RefEntityType.TEAM)));
        for (DataMapping mapping : mappings) {
            dataMappingRepository.delete(mapping);
        }
        bookMakerSynchronizerService.synchronize(bookMaker);
        Iterable<UnMatchedPlayer> iterable = unMatchedPlayerRepository
                .findAll(QUnMatchedPlayer.unMatchedPlayer.bookMaker
                        .eq(bookMaker));
        List<UnMatchedPlayer> unmatched = Lists.newArrayList(iterable);
        List<String> teams = new ArrayList<String>(500);
        Scanner scanner = new Scanner(new File(
                "/Users/marcherman/Documents/workspace/superbet/team.txt"));
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            teams.add(line);
        }
        List<StringMatcher> matchers = Lists.newArrayList(
                new IgnoreCaseMatcher(), new SimilarMatcher(0.8));
        NoiseTeamRemover noiseTeamRemover = new NoiseTeamRemover();
        List<DataMapping> matched = new LinkedList<DataMapping>();
        for (UnMatchedPlayer player : unmatched) {
            match(bookMaker, teams, matched, player, matchers, noiseTeamRemover);
        }
        System.out.println("Non mais alo koi " + matched.size() + "/"
                + unmatched.size());
        GraphResolver graphResolver = new SuperBetGraphResolver(
                repositoryRegistry);
        CsvContext<DataMapping> context = CsvContext.create(DataMapping.class,
                graphResolver);
        CsvMarshaller<DataMapping> marshaller = context.newMarshaller();
        marshaller.marshall(matched, System.out);
    }

    private void match(BookMaker bookMaker, List<String> teams,
            List<DataMapping> matched, UnMatchedPlayer player,
            List<StringMatcher> matchers, NoiseTeamRemover noiseTeamRemover) {
        String cleanBookmaker = noiseTeamRemover.removeNoise(player.getCode());
        for (String team : teams) {
            for (StringMatcher matcher : matchers) {
                String cleanModel = noiseTeamRemover.removeNoise(team);
                if (matcher.match(cleanBookmaker, cleanModel)) {
                    DataMapping mapping = new DataMapping();
                    mapping.setBookMaker(bookMaker);
                    mapping.setBookMakerCode(player.getCode());
                    mapping.setModelCode(team);
                    mapping.setRefEntityType(RefEntityType.TEAM);
                    matched.add(mapping);
                    return;
                }
            }
        }
    }
}
