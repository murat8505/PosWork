package com.Utils;

public class CardValidatorClass {

	public String getCCType(String ccNumber){
	

		String visaRegex        = "^4[0-9]{12}(?:[0-9]{3})?$";
		String masterRegex      = "^5[1-5][0-9]{14}$";
		String amexRegex        = "^3[47][0-9]{13}$";
		String dinersClubrRegex = "^3(?:0[0-5]|[68][0-9])[0-9]{11}$";
		String discoverRegex    = "^6(?:011|5[0-9]{2})[0-9]{12}$";
		String jcbRegex         = "^(?:2131|1800|35\\d{3})\\d{11}$";
		//String commonRegex      = "^(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})$";

		try {
			ccNumber = ccNumber.replaceAll("\\D", "");
			return (ccNumber.matches(visaRegex) ? "Visa" : ccNumber.matches(masterRegex) ? "MasterCard" :ccNumber.matches(amexRegex) ? "AMEX" :ccNumber.matches(dinersClubrRegex) ? "DinersClub" :ccNumber.matches(discoverRegex) ? "Discover"  :ccNumber.matches(jcbRegex) ? "JCB":"");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public String getCCTypeShotLeter(String ccNumber){

		String visaRegex        = "^4[0-9]{12}(?:[0-9]{3})?$";
		String masterRegex      = "^5[1-5][0-9]{14}$";
		String amexRegex        = "^3[47][0-9]{13}$";
		String dinersClubrRegex = "^3(?:0[0-5]|[68][0-9])[0-9]{11}$";
		String discoverRegex    = "^6(?:011|5[0-9]{2})[0-9]{12}$";
		String jcbRegex         = "^(?:2131|1800|35\\d{3})\\d{11}$";
		//String commonRegex      = "^(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})$";

		try {
			ccNumber = ccNumber.replaceAll("\\D", "");
			return (ccNumber.matches(visaRegex) ? "VI" : ccNumber.matches(masterRegex) ? "MC" :ccNumber.matches(amexRegex) ? "AE" :ccNumber.matches(dinersClubrRegex) ? "OT" :ccNumber.matches(discoverRegex) ? "DI"  :ccNumber.matches(jcbRegex) ? "JCB":"");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public boolean isValidCardNumber(String ccNumber){

		try {
			ccNumber = ccNumber.replaceAll("\\D", "");
			char[]      ccNumberArry    = ccNumber.toCharArray();

			int         checkSum        = 0;
			for(int i = ccNumberArry.length - 1; i >= 0; i--){

				char            ccDigit     = ccNumberArry[i];

				if((ccNumberArry.length - i) % 2 == 0){
					int doubleddDigit = Character.getNumericValue(ccDigit) * 2;
					checkSum    += (doubleddDigit % 9 == 0 && doubleddDigit != 0) ? 9 : doubleddDigit % 9;

				}else{
					checkSum    += Character.getNumericValue(ccDigit);
				}

			}

			return (checkSum != 0 && checkSum % 10 == 0);

		} catch (Exception e) {

			e.printStackTrace();

		}

		return false;
	}
}
