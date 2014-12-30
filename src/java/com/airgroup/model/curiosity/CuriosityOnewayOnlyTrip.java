package com.airgroup.model.curiosity;

/**
 * @author linhnd1
 *
 */
public class CuriosityOnewayOnlyTrip  extends CuriosityTrip{
	private CuriosityTrip outboundTrip;
	private CuriosityTrip inboundTrip;

	public CuriosityTrip getOutboundTrip() {
		return this.outboundTrip;
	}

	public void setOutboundTrip(CuriosityTrip outboundTrip) {
		this.outboundTrip = outboundTrip;
	}

	public CuriosityTrip getInboundTrip() {
		return this.inboundTrip;
	}

	public void setInboundTrip(CuriosityTrip inboundTrip) {
		this.inboundTrip = inboundTrip;
	}
}
