package com.cellfish.mediadb.account.server.rpc;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ServiceDispatcher extends RemoteServiceServlet {

	private static final long serialVersionUID = 5713342995532698061L;

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceDispatcher.class);

	private ApplicationContext applicationContext;

	private Properties mapping;

	@Override
	public String processCall(String payload) throws SerializationException {
		try {
			String serviceName = extractServiceName(getThreadLocalRequest().getServletPath());
			Object service = applicationContext.getBean(serviceName);

			RPCRequest rpcRequest = RPC.decodeRequest(payload, service.getClass(), this);
			onAfterRequestDeserialized(rpcRequest);

			return RPC.invokeAndEncodeResponse(service, rpcRequest.getMethod(), rpcRequest.getParameters(),
					rpcRequest.getSerializationPolicy(), rpcRequest.getFlags());
		} catch (IncompatibleRemoteServiceException ex) {
			log("An IncompatibleRemoteServiceException was thrown while processing this call.", ex);
			return RPC.encodeResponseForFailure(null, ex);
		}
	}

	protected String extractServiceName(String servletPath) throws IncompatibleRemoteServiceException {
		String serviceName = mapping.getProperty(servletPath.substring(servletPath.lastIndexOf("/") + 1));
		if (serviceName == null) {
			throw new IncompatibleRemoteServiceException("The requested service (" + servletPath + ") cannot be found.");
		}
		if (serviceName.endsWith(" ")) {
			LOGGER.error("Le nom de service obtenu par le mapping se finit par un espace. "
					+ "Vérifiez la configuration du fichier de mapping entre les RPC et les beans spring.");
		}
		return serviceName;
	}

	/**
	 * La surchage de cette méthode permet de charger le mapping du dispatcher
	 * et le contexte applicatif de Spring. Il s'appuie sur l'"init-param"
	 * config qui indique ou trouver la configuration de mapping. Exemple, dans
	 * le web.xml il faut ajouter :
	 * 
	 * <pre>
	 * &lt;init-param&gt;
	 * 	&lt;param-name&gt;config&lt;/param-name&gt;
	 * 	&lt;param-value&gt;mapping.properties&lt;/param-value&gt;
	 * &lt;/init-param&gt;
	 * </pre>
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String configLocation = config.getInitParameter("config");
		if (configLocation == null) {
			throw new ServletException("Could not find mapping for the service dispatcher.");
		}
		ClassPathResource resource = new ClassPathResource(configLocation);
		try {
			mapping = new Properties();
			mapping.load(resource.getInputStream());
		} catch (IOException e) {
			throw new ServletException("Could not find mapping for the service dispatcher.", e);
		}

		applicationContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		LOGGER.debug("ServiceDispatcher démarré");
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
}
