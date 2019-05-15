package yt.dubbo.log;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


@Plugin(name = "dubboAreaAppender", category = "Core", elementType = "appender", printObject = true)
public class DubboAreaAppender extends AbstractAppender {

	/**
	 * @fields serialVersionUID
	 */
	private static final long serialVersionUID = -830237775522429777L;
	private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
	private final Lock readLock = rwLock.readLock();

	//需要实现的构造方法，直接使用父类就行
	protected DubboAreaAppender(final String name, final Filter filter, final Layout<? extends Serializable> layout,
							   final boolean ignoreExceptions) {
		super(name, filter, layout, ignoreExceptions);
	}

	@Override
	public void append(LogEvent logEvent) {
		readLock.lock();
		//      只记录INFO级别以上的日志
		if (logEvent.getLevel().isInRange(Level.FATAL, Level.INFO)) {
			try {
				String returnContent = new String(getLayout().toByteArray(logEvent), "UTF-8");
				//对日志内容做处理
			} catch (UnsupportedEncodingException e) {

			}finally {
				readLock.unlock();
			}
		}
	}

	// 下面这个方法可以接收配置文件中的参数信息
	@PluginFactory
	public static DubboAreaAppender createAppender(@PluginAttribute("name") String name,
												  @PluginElement("Filter") final Filter filter,
												  @PluginElement("Layout") Layout<? extends Serializable> layout,
												  @PluginAttribute("ignoreExceptions") boolean ignoreExceptions) {
		if (name == null) {
			LOGGER.error("No name provided for MyCustomAppenderImpl");
			return null;
		}
		if (layout == null) {
			layout = PatternLayout.createDefaultLayout();
		}
		return new DubboAreaAppender(name, filter, layout, ignoreExceptions);
	}
}
