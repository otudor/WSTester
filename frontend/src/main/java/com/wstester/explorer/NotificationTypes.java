package com.wstester.explorer;

public enum NotificationTypes {
	
	DELETE_SUCCESFULL{
		public String toString() {
			return "Delete operation for: % was succesfull";
		}
	},
	DELETE_UNSUCCESFULL{
		public String toString() {
			return "Delete operation for: % was unsuccesfull";
		}
	},
	COPY_SUCCESFULL{
		public String toString() {
			return "Copy operation for: % was succesfull";
		}
	},
	COPY_UNSUCCESFULL{
		public String toString() {
			return "Copy operation for: % was unsuccesfull";
		}
	},

}
