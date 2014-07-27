package org.aksw.hawk.controller;

import java.io.File;
import java.io.IOException;

import org.aksw.autosparql.commons.qald.QALD_Loader;
import org.aksw.hawk.cache.AbstractIndexCache;
import org.aksw.hawk.index.DBAbstractsIndex;
import org.aksw.hawk.module.Fulltexter;
import org.aksw.hawk.module.ModuleBuilder;
import org.aksw.hawk.module.PseudoQueryBuilder;
import org.aksw.hawk.nlp.Annotater;
import org.aksw.hawk.nlp.Pruner;
import org.aksw.hawk.nlp.SentenceToSequence;
import org.aksw.hawk.nlp.spotter.Fox;
import org.aksw.hawk.nlp.util.CachedParseTree;
import org.aksw.hawk.pruner.GraphNonSCCPruner;
import org.aksw.hawk.pruner.QueryVariableHomomorphPruner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QALD4_short {
	static Logger log = LoggerFactory.getLogger(QALD4_short.class);

	public static void main(String args[]) throws IOException {

		for (String file : new String[] { "resources/qald-4_hybrid_train.xml" }) { // ,"resources/qald-4_multilingual_train_withanswers.xml"
			PipelineShortRecall controller = new PipelineShortRecall();

			log.info("Configuring controller");

			controller.dataset = new File(file).getAbsolutePath();
			controller.datasetLoader = new QALD_Loader();
			controller.nerdModule = new Fox();
			controller.cParseTree = new CachedParseTree();

			AbstractIndexCache cache = new AbstractIndexCache();
			DBAbstractsIndex index = new DBAbstractsIndex(cache);
			controller.sentenceToSequence = new SentenceToSequence(index);
			controller.fulltexter = new Fulltexter(index);
			controller.queryBuilder = new SPARQLQueryBuilder(index);
			controller.annotater = new Annotater(index);
			
			controller.pruner = new Pruner();
			controller.moduleBuilder = new ModuleBuilder();
			controller.pseudoQueryBuilder = new PseudoQueryBuilder();
			controller.queryVariableHomomorphPruner = new QueryVariableHomomorphPruner();
			controller.graphNonSCCPruner = new GraphNonSCCPruner();
			String endpoint = "http://dbpedia.org/sparql";
			log.info("Run controller");
			controller.run();
			//TODO if transformed to webapp solve caching more clever
			cache.writeCache();
		}
	}
}
