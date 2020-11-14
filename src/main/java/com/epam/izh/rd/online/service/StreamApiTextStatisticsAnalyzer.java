package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.helper.Direction;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Данный класс обязан использовать StreamApi из функционала Java 8. Функциональность должна быть идентична
 * {@link SimpleTextStatisticsAnalyzer}.
 */
public class StreamApiTextStatisticsAnalyzer implements TextStatisticsAnalyzer {
    @Override
    public int countSumLengthOfWords(String text) {
        return getWords(text)
                .stream()
                .mapToInt(w -> w.length())
                .sum();
    }

    @Override
    public int countNumberOfWords(String text) {
        return (int) getWords(text).stream().count();
    }

    @Override
    public int countNumberOfUniqueWords(String text) {
        return (int) getWords(text).stream().distinct().count();
    }

    @Override
    public List<String> getWords(String text) {
        return Pattern.compile("\\W").splitAsStream(text)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Set<String> getUniqueWords(String text) {
        return getWords(text)
                .stream()
                .collect(Collectors.toCollection(HashSet::new));
    }

    @Override
    public Map<String, Integer> countNumberOfWordsRepetitions(String text) {
        List<String> wordsList = getWords(text);
        Map<String, Integer> frequencyMap = new HashMap<>();
        wordsList.stream()
                .distinct()
                .forEach(element -> frequencyMap.put(element, Collections.frequency(wordsList, element)));

        return frequencyMap;
    }

    @Override
    public List<String> sortWordsByLength(String text, Direction direction) {
        if (direction == Direction.DESC) {
            return getWords(text)
                    .stream()
                    .sorted(new StringDESCLengthComparator())
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        return getWords(text)
                .stream()
                .sorted(new StringASCLengthComparator())
                .collect(Collectors.toCollection(ArrayList::new));
    }
}