//package io.ruin.model.inter.handlers;
//
//import com.paypal.api.payments.*;
//import com.paypal.base.rest.APIContext;
//import com.paypal.base.rest.PayPalRESTException;
//import io.ruin.api.utils.ServerWrapper;
//import io.ruin.model.World;
//import io.ruin.model.entity.player.Player;
//import io.ruin.model.inter.InterfaceType;
//import io.ruin.model.inter.dialogue.ItemDialogue;
//import io.ruin.model.inter.dialogue.MessageDialogue;
//import io.ruin.model.inter.dialogue.OptionsDialogue;
//import io.ruin.model.inter.utils.Option;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class BuyCredits {
//
//    public static final String PAYPAL_CLIENT_ID = "AZw49pmnM8FvcMiDRvU2nwaZG0RHnbOzisfuAMhakDiwdD2nd7q483LspFenfoyhUGeVpbN0Oo883fvi";
//
//    public static final String PAYPAL_SECRET = "EMnM3Tv72wkxgA8SUeiPhV6If_RS44IJougUU5V5Uds3RxCJgL2Z5lGNd8s9bYOc6774fTSAczFJGwIO";
//
//    //todo ^ set to LIVE credentials
//
//    public final int purchasePrice;
//
//    public final int purchaseAmount;
//
//    public final int freeAmount;
//
//    private BuyCredits(int purchasePrice, int purchaseAmount, int freeAmount) {
//        this.purchasePrice = purchasePrice;
//        this.purchaseAmount = purchaseAmount;
//        this.freeAmount = freeAmount;
//    }
//
//    private static void send(Player player) {
//        if(player.isVisibleInterface(66))
//            player.closeInterface(InterfaceType.MAIN);
//        player.openInterface(InterfaceType.MAIN, 66);
//        player.getPacketSender().sendBuyCredits(
//                CUSTOM_MESSAGE,
//                DISCOUNT_PERCENT,
//                player.selectedCreditPackage,
//                player.selectedPaymentMethod,
//                PACKS
//        );
//    }
//
//    public static void open(Player player) {
//        player.selectedCreditPackage = -1;
//        player.selectedPaymentMethod = 0;
//        send(player);
//    }
//
//    private static void selectCreditPackage(Player player, int creditPackage) {
//        if(player.selectedCreditPackage == creditPackage)
//            return;
//        player.selectedCreditPackage = creditPackage;
//        send(player);
//    }
//
//    private static void selectPaymentMethod(Player player, int paymentMethod) {
//        if(player.selectedPaymentMethod == paymentMethod)
//            return;
//        player.selectedPaymentMethod = paymentMethod;
//        send(player);
//    }
//
//    private static void checkout(Player player) {
//        if(true) {
//            player.dialogue(new MessageDialogue("Coming Soon!"));
//            return;
//        }
//        if(player.isLocked())
//            return;
//        if(player.selectedCreditPackage == -1) {
//            player.unsafeDialogue(new ItemDialogue().one(13190, "Please select a credit package before checking out."));
//            return;
//        }
//        if(player.selectedPaymentMethod == 0) {
//            //todo
//        } else {
//            Amount amount = new Amount();
//            amount.setCurrency("USD");
//            amount.setTotal("1.00");
//
//            Transaction transaction = new Transaction();
//            transaction.setAmount(amount);
//            List<Transaction> transactions = new ArrayList<Transaction>();
//            transactions.add(transaction);
//
//            Payer payer = new Payer();
//            payer.setPaymentMethod("paypal");
//
//            Payment payment = new Payment();
//            payment.setIntent("sale");
//            payment.setPayer(payer);
//            payment.setTransactions(transactions);
//
//            RedirectUrls redirectUrls = new RedirectUrls();
//            redirectUrls.setCancelUrl("https://www.runite.io/");
//            redirectUrls.setReturnUrl("https://www.runite.io/");
//            payment.setRedirectUrls(redirectUrls);
//            try {
//                APIContext context = new APIContext(PAYPAL_CLIENT_ID, PAYPAL_SECRET, "sandbox");
//                Payment createdPayment = payment.create(context);
//                System.err.println(Payment.getLastResponse()); //hmm??
//                System.err.println("-------");
//                System.err.println(createdPayment.toString());
//            } catch (PayPalRESTException e) {
//                // Handle errors
//                ServerWrapper.logError("Failed buying credits", e);
//            }
//        }
//        if(true) return;
//        String url = "https://www.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-5N743429B7163702M";
//        //todo ^
//        player.addEvent(e -> {
//            player.lock();
//            player.unsafeDialogue(new MessageDialogue("<col=880000>Opening Checkout Page...").hideContinue());
//            player.getPacketSender().sendUrl(url, true);
//            e.delay(5);
//            player.unsafeDialogue(new OptionsDialogue("How was your checkout?",
//                    new Option("My checkout page never opened.", () -> {
//                        player.unsafeDialogue(new MessageDialogue("The checkout page link has been copied to your clipboard.<br>Paste it in your preferred web browser to open the checkout page.<br>We're sorry very for the inconvenience!").lineHeight(24));
//                    }),
//                    new Option("It went well, how do I claim my credits?", () -> {
//                        player.unsafeDialogue(new MessageDialogue("After you have purchased credits, you can claim them from the Credit Manager in Edgeville or by using the ::claim command.").lineHeight(24));
//                    }),
//                    new Option("I don't need any additional help.")
//            ));
//            player.unlock();
//        });
//    }
//
//    /*static {
//        InterfaceHandler.register(66, h -> {
//            h.actions[4] = (SimpleAction) p -> selectCreditPackage(p, 0);
//            h.actions[15] = (SimpleAction) p -> selectCreditPackage(p, 1);
//            h.actions[27] = (SimpleAction) p -> selectCreditPackage(p, 2);
//            h.actions[40] = (SimpleAction) p -> selectCreditPackage(p, 3);
//            h.actions[62] = (SimpleAction) p -> selectCreditPackage(p, 4);
//            h.actions[73] = (SimpleAction) p -> selectCreditPackage(p, 5);
//            h.actions[3] = (SlotAction) (p, slot) -> {
//                if(slot == 15) //stripe
//                    selectPaymentMethod(p, 0);
//                else if(slot == 17) //paypal
//                    selectPaymentMethod(p, 1);
//            };
//            h.actions[94] = (SimpleAction) BuyCredits::checkout;
//        });
//    }*/
//
//    /**
//     * Misc
//     */
//
//    private static String CUSTOM_MESSAGE = "";
//
//    private static int DISCOUNT_PERCENT = 0;
//
//    private static final BuyCredits[] PACKS = {
//            new BuyCredits(5, 500, 0),
//            new BuyCredits(10, 1000, 100),
//            new BuyCredits(25, 2500, 500),
//            new BuyCredits(50, 5000, 1500),
//            new BuyCredits(100, 10000, 4000),
//            new BuyCredits(250, 25000, 12500),
//    };
//
//    public static void set(String message, int percent) {
//        CUSTOM_MESSAGE = message;
//        DISCOUNT_PERCENT = percent;
//        for(Player player : World.players) {
//            if(player.isVisibleInterface(66))
//                send(player);
//        }
//    }
//
//    public static void setMessage(String message) {
//        set(message, DISCOUNT_PERCENT);
//    }
//
//    public static void setDiscount(int percent) {
//        set(CUSTOM_MESSAGE, percent);
//    }
//
//}