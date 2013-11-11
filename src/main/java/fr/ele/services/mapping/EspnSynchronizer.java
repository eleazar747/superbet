package fr.ele.services.mapping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import fr.ele.model.ref.Sport;
import fr.ele.services.repositories.DataMappingRepository;

public class EspnSynchronizer {
    @Autowired
    private DataMappingRepository dataMappingRepository;

    public static class EspnSynchronizeContext {
        private List<Sport> sports;

        private long intervalLimit;

        private int maxCalls;

        public List<Sport> getSports() {
            return sports;
        }

        public void setSports(List<Sport> sports) {
            this.sports = sports;
        }

        public long getIntervalLimit() {
            return intervalLimit;
        }

        public void setIntervalLimit(long intervalLimit) {
            this.intervalLimit = intervalLimit;
        }

        public int getMaxCalls() {
            return maxCalls;
        }

        public void setMaxCalls(int maxCalls) {
            this.maxCalls = maxCalls;
        }

    }

    public void synchronize() {

    }
}
