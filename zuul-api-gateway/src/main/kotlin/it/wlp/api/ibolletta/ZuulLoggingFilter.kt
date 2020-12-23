package it.wlp.api.ibolletta

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/api-rest")
class ZuulLoggingFilter : ZuulFilter() {

    companion object {
        private val logger
                = LoggerFactory.getLogger(ZuulLoggingFilter::class.java)
    }

    override fun shouldFilter(): Boolean {
        return true
    }

    override fun run(): Any {
        logger.info("Request is filtered");
        val httpServletRequest = RequestContext.getCurrentContext().getRequest();
        return logger.info("request -> {} request uri -> {} ", httpServletRequest, httpServletRequest.getRequestURI());
    }

    override fun filterType(): String {
        return "pre"
    }

    override fun filterOrder(): Int {
        return 1
    }

}