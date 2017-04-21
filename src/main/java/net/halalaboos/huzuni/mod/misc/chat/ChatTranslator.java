//package net.halalaboos.huzuni.mod.misc.chat;
//
//import com.gtranslate.Language;
//import com.gtranslate.Translator;
//import net.halalaboos.huzuni.api.mod.BasicMod;
//import net.halalaboos.huzuni.api.node.Mode;
//import net.halalaboos.huzuni.api.node.impl.Toggleable;
//import net.halalaboos.mcwrapper.api.event.network.PacketSendEvent;
//import net.halalaboos.mcwrapper.api.network.packet.client.ChatMessagePacket;
//
///**
// * Automatically translates chat messages using google's text translation api. <br/>
// * Created by Brandon Williams on 4/20/2017.
// */
//public class ChatTranslator extends BasicMod {
//
//    private Toggleable detectLanguage = new Toggleable("Detect", "Detect the language provided.");
//
//    private Toggleable ignoreCommands = new Toggleable("Ignore commands", "Detect the language provided.");
//
//    private Mode<String> fromLanguage = new Mode<>("From", "Choose a language to translate from.");
//
//    private Mode<String> toLanguage = new Mode<>("To", "Choose a language to translate to.");
//
//    public ChatTranslator() {
//        super("Chat translator", "Translates chat messages from one language to another.");
//        this.addChildren(detectLanguage, ignoreCommands, fromLanguage, toLanguage);
//        detectLanguage.setEnabled(true);
//        ignoreCommands.setEnabled(true);
//        String[] languages = {
//                Language.ENGLISH,
//                Language.FRENCH,
//                Language.GERMAN
//        };
//        for (String language : languages) {
//            toLanguage.add(language);
//            fromLanguage.add(language);
//        }
//        subscribe(PacketSendEvent.class, (event) -> {
//            if (event.getPacket() instanceof ChatMessagePacket) {
//                ChatMessagePacket packetChatMessage = (ChatMessagePacket) event.getPacket();
//                String message = packetChatMessage.getText();
//                if (!ignoreCommands.isEnabled() || (message.startsWith("/") || message.startsWith(huzuni.commandManager.getCommandPrefix()))) {
//                    packetChatMessage.setText(Translator.getInstance().translate(message, detectLanguage.isEnabled() ? Translator.getInstance().detect(message) : fromLanguage.getSelectedItem(), toLanguage.getSelectedItem()));
//                }
//                packetChatMessage.setText(message);
//            }
//        });
//    }
//
//}
