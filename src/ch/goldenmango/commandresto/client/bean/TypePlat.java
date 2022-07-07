package ch.goldenmango.commandresto.client.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//import com.google.gwt.user.client.rpc.IsSerializable;


public class TypePlat  implements Serializable /*, IsSerializable*/{
		
		private String descr;
		private List<Plat> plats;
		
		public String getDescr() {
			return descr;
		}
		public void setDescr(String descr) {
			this.descr = descr;
		}
		public List<Plat> getPlats() {
			return plats;
		}
		public void setPlats(List<Plat> plats) {
			this.plats = plats;
		}

		
//		public String getDescr() {
//			return descr;
//		}
//
//		public void setDescr(String descr) {
//			this.descr = descr;
//		}
//
//		public ArrayList<Plat> getPlats() {
//			return plats;
//		}
//
//
//
////		public TypePlat() {
////			super();
////		}
//
////		public TypePlat(String descr) {
////			this.descr = descr;
////
////		}
//
//		public String descr() {
//			return descr;
//		}
//		
//		public ArrayList<Plat> plats() {
//			return plats;
//		}
//
//		public void setPlats(ArrayList<Plat> plats) {
//			this.plats = plats;
//		}
		
}
