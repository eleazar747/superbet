package fr.ele.services.mapping;

import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import fr.ele.model.QUnMatchedPlayer;
import fr.ele.model.UnMatchedPlayer;
import fr.ele.model.ref.Sport;
import fr.ele.services.repositories.BetTypeRepository;
import fr.ele.services.repositories.BookMakerRepository;
import fr.ele.services.repositories.DataMappingRepository;
import fr.ele.services.repositories.MatchRepository;
import fr.ele.services.repositories.RefKeyRepository;
import fr.ele.services.repositories.SportRepository;
import fr.ele.services.repositories.UnMatchedPlayerRepository;

public class MappingControlContext extends SynchronizerContext {

    private final UnMatchedPlayerRepository unMatchedPlayerRepository;

    private final Set<String> cache;

    public MappingControlContext(String bookmakerCode,
            DataMappingRepository dataMappingRepository,
            SportRepository sportRepository,
            BetTypeRepository betTypeRepository,
            BookMakerRepository bookMakerRepository,
            MatchRepository matchRepository, RefKeyRepository refKeyRepository,
            UnMatchedPlayerRepository unMatchedPlayerRepository) {
        super(bookmakerCode, dataMappingRepository, sportRepository,
                betTypeRepository, bookMakerRepository, matchRepository,
                refKeyRepository);
        this.unMatchedPlayerRepository = unMatchedPlayerRepository;
        Iterable<UnMatchedPlayer> all = unMatchedPlayerRepository
                .findAll(QUnMatchedPlayer.unMatchedPlayer.bookMaker
                        .eq(getBookMaker()));
        Iterable<String> codes = Iterables.transform(all,
                new Function<UnMatchedPlayer, String>() {

                    @Override
                    @Nullable
                    public String apply(@Nullable UnMatchedPlayer input) {
                        return input.getCode();
                    }
                });
        cache = Sets.newHashSet(codes);

    }

    @Override
    public String findTeam(Sport sport, String bookMakerSportCode) {
        String team = super.findTeam(sport, bookMakerSportCode);
        if (team == null && !cache.contains(bookMakerSportCode)) {
            UnMatchedPlayer unMatchedPlayer = new UnMatchedPlayer();
            unMatchedPlayer.setBookMaker(getBookMaker());
            unMatchedPlayer.setCode(bookMakerSportCode);
            unMatchedPlayerRepository.save(unMatchedPlayer);
        }
        return team;
    }
}
