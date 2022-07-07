package ch.goldenmango.commandresto.client.event;

import com.google.gwt.event.shared.GwtEvent;


public class DataReceivedEvent extends GwtEvent<DataReceivedHandler> {

		public static final Type<DataReceivedHandler> TYPE = new Type<DataReceivedHandler>();
		
		public static enum TypeReceived {
			INFO,
			RESOURCES,
			VALUESET,
			FAIL
		};
		
		private TypeReceived typeReceived;
		
		private String itemId;
		
		public DataReceivedEvent() {
			super();
		}
		
		public DataReceivedEvent(TypeReceived typeReceived) {
			super();
			this.typeReceived = typeReceived;
		}
		// for filter valueset editor
		public DataReceivedEvent(TypeReceived typeReceived, String itemId) {
			this(typeReceived);
			this.itemId = itemId;
		}

		@Override
		protected void dispatch(DataReceivedHandler handler) {
			handler.onDataReceived(this);
		}

		@Override
		public com.google.gwt.event.shared.GwtEvent.Type<DataReceivedHandler> getAssociatedType() {
			return TYPE;
		}

		public TypeReceived getTypeReceived() {
			return typeReceived;
		}

		public void setTypeReceived(TypeReceived typeReceived) {
			this.typeReceived = typeReceived;
		}
		public String getItemId() {
			return itemId;
		}
		public void setItemId(String itemId) {
			this.itemId = itemId;
		}

}
