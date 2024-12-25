//package io.ruin.services;
//
//import com.paypal.api.payments.*;
//import com.paypal.base.rest.APIContext;
//
//public class PayPal {
//
//    private static final Info SANDBOX = new Info(
//            true, "AZw49pmnM8FvcMiDRvU2nwaZG0RHnbOzisfuAMhakDiwdD2nd7q483LspFenfoyhUGeVpbN0Oo883fvi",
//            "EMnM3Tv72wkxgA8SUeiPhV6If_RS44IJougUU5V5Uds3RxCJgL2Z5lGNd8s9bYOc6774fTSAczFJGwIO",
//            "XP-8FHE-7TTR-4ZC3-GYCG"
//    );
//
//    private static final Info LIVE = new Info(
//            false, "todo",
//            "todo",
//            "todo"
//    );
//
//    public static void main(String[] args) throws Exception {
//        //SANDBOX.updateWebProfile();
//        EventTypeList list = EventType.availableEventTypes(SANDBOX.context());
//        for(EventType type : list.getEventTypes()) {
//            System.out.println(type.getName() + ": " + type.getDescription());
//        }
//        System.out.println("Welp.. NONE?");
//    }
//
//    private static final class Info {
//
//        public final boolean sandbox;
//        public final String clientId, secret;
//        public final String webProfileId;
//
//        private Info(boolean sandbox, String clientId, String secret, String webProfileId) {
//            this.clientId = clientId;
//            this.secret = secret;
//            this.webProfileId = webProfileId;
//            this.sandbox = sandbox;
//        }
//
//        public void updateWebProfile() throws Exception {
//            APIContext context = context();
//            WebProfile profile = WebProfile.get(context, webProfileId);
//            boolean update = true;
//            if(profile == null) {
//                profile = new WebProfile();
//                update = false;
//            }
//            FlowConfig config = new FlowConfig()
//                    .setLandingPageType("Billing")
//                    .setUserAction("commit")
//                    .setReturnUriHttpMethod("POST");
//
//            Presentation presentation = new Presentation()
//                    .setLogoImage("https://www.runite.io/img/logo-med.png")
//                    .setBrandName("OSPvP.com")
//                    .setLocaleCode("US")
//                    .setReturnUrlLabel("Return");
//
//            InputFields inputFields = new InputFields()
//                    .setAllowNote(false)
//                    .setNoShipping(1)
//                    .setAddressOverride(0);
//
//            profile.setName("runite_credit_store" + (sandbox ? "_SANDBOX" : ""))
//                    .setFlowConfig(config)
//                    .setPresentation(presentation)
//                    .setInputFields(inputFields)
//                    .setTemporary(false);
//
//            if(update) {
//                profile.update(context);
//                System.err.println("Updated web profile '" + webProfileId + "' successfully!");
//            } else {
//                CreateProfileResponse response = profile.create(context);
//                System.err.println(response.toJSON());
//            }
//        }
//
//        public APIContext context() {
//            return new APIContext(clientId, secret, sandbox ? "sandbox" : "live");
//        }
//
//    }
//
//}