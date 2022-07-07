package ch.goldenmango.commandresto.client.view.payement;

import com.google.gwt.user.client.Window;

import gwt.material.design.jquery.client.api.Functions.Func;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(namespace = JsPackage.GLOBAL, name = "Object", isNative = true)
public class DatatransOption {
	@JsProperty
	public Func opened;

	@JsProperty
	public Func loaded;

	@JsProperty
	public Func closed;

	@JsProperty
	public Func error;

	@JsOverlay
	public static DatatransOption create(String transactionId)
	{
		DatatransOption option = new DatatransOption();
		option.transactionId = transactionId;
		option.opened = new Func() {
			
			@Override
			public void call() {
				Window.alert("opened");
			}
		};
		option.loaded = new Func() {
			
			@Override
			public void call() {
				Window.alert("loaded");
			}
		};
		option.closed = new Func() {
			
			@Override
			public void call() {
				Window.alert("closed");
			}
		};
		option.error = new Func() {
			
			@Override
			public void call() {
				Window.alert("error");
			}
		};
		return option;
	}

	@JsProperty
	public String transactionId;

//	@JsMethod
//	public void opened() {
//		Window.alert("payment form opened");
//	}
//
//	@JsMethod
//	public void loaded() {
//		Window.alert("payment form loaded");
//	}
//	@JsMethod
//	public void closed() {
//		Window.alert("payment form closed");
//	}
//	@JsMethod
//	public void error() {
//		Window.alert("payment form error");
//	}

}
