package ch.goldenmango.commandresto.client.view.payement;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(namespace = JsPackage.GLOBAL, isNative = true)
public class Datatrans {
	public native static void startPayment(DatatransOption option);
}
