package searchengine.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import searchengine.model.LemmaEntity;
import searchengine.model.SiteEntity;
import searchengine.repositories.LemmaRepository;
import searchengine.services.LemmaService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LemmaServiceImpl implements LemmaService {

    private final LemmaRepository lemmaRepository;

    public synchronized Map<LemmaEntity, Integer> addLemma(Map<String, Integer> lemmas, SiteEntity siteEntity) {
        LemmaEntity lemmaEntity;
        Map<LemmaEntity, Integer> map = new HashMap<>();
        for (String word : lemmas.keySet()) {
            Optional<LemmaEntity> optionalLemma = findLemmaEntityByLemmaAndSiteEntity(word, siteEntity);
            if (optionalLemma.isPresent()) {
                lemmaEntity = optionalLemma.get();
                lemmaEntity.setFrequency(lemmaEntity.getFrequency() + 1);
            } else {
                lemmaEntity = new LemmaEntity();
                lemmaEntity.setSiteEntity(siteEntity);
                lemmaEntity.setFrequency(1);
                lemmaEntity.setLemma(word);
            }
            lemmaRepository.save(lemmaEntity);
            map.put(lemmaEntity, lemmas.get(word));
        }
        return map;
    }

    public Optional<LemmaEntity> findLemmaEntityByLemmaAndSiteEntity(String lemma, SiteEntity siteEntity) {
        return lemmaRepository.findByLemmaAndSiteEntity(lemma, siteEntity);
    }
}
