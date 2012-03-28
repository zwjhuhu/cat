package com.dianping.cat.consumer;

import com.dianping.cat.consumer.event.EventAnalyzer;
import com.dianping.cat.consumer.ip.IpAnalyzer;
import com.dianping.cat.consumer.problem.ProblemAnalyzer;
import com.dianping.cat.consumer.transaction.TransactionAnalyzer;
import com.dianping.cat.message.spi.MessageAnalyzer;
import com.site.lookup.ContainerHolder;
import com.site.lookup.annotation.Inject;

/**
 * @author yong.you
 * @since Jan 5, 2012
 */
public class DefaultAnalyzerFactory extends ContainerHolder implements AnalyzerFactory {
	@Inject
	private boolean m_local;

	@Override
	public MessageAnalyzer create(String name, long start, long duration, long extraTime) {
		if (name.equals("problem")) {
			ProblemAnalyzer analyzer = lookup(ProblemAnalyzer.class);

			analyzer.setAnalyzerInfo(start, duration, extraTime);
			analyzer.setLocal(m_local);
			return analyzer;
		} else if (name.equals("transaction")) {
			TransactionAnalyzer analyzer = lookup(TransactionAnalyzer.class);

			analyzer.setAnalyzerInfo(start, duration, extraTime);
			analyzer.setLocal(m_local);
			return analyzer;
		} else if (name.equals("event")) {
			EventAnalyzer analyzer = lookup(EventAnalyzer.class);

			analyzer.setAnalyzerInfo(start, duration, extraTime);
			analyzer.setLocal(m_local);
			return analyzer;
		} else if (name.equals("ip")) {
			IpAnalyzer analyzer = lookup(IpAnalyzer.class);

			return analyzer;
		}

		throw new RuntimeException(String.format("No analyzer(%s) found!", name));
	}

	@Override
	public void release(Object component) {
		super.release(component);
	}

	/**
	 * Set local mode. In local mode, all reports and log-views will only be
	 * stored in local disk, no reports or log-views will be stored in HDFS or
	 * MySQL. <p>
	 * 
	 * @param local
	 */
	public void setLocal(boolean local) {
		m_local = local;
	}
}
