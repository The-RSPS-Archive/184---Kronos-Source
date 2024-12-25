package io.ruin.api.utils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class PacketSizeTool {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("hh:mm:ss a");

    private static final int NON_EXISTENT_SIZE = Byte.MIN_VALUE;

    private static final int UNDETERMINED_SIZE = Integer.MAX_VALUE;

    private static final String IDENTIFIER = "addOpcode";

    private static final int[] SIZES = new int[256];

    private static final PacketBlock[] PACKETS = new PacketBlock[256];

    private static final class PacketFrame extends JFrame {

        public final JLabel opcodeLabel = new JLabel();
        public final JLabel estimatedLabel = new JLabel();
        public final JTextArea codeArea = new JTextArea();
        public final JScrollPane codeAreaPane = new JScrollPane(codeArea);
        public final JTextField sizeField = new JTextField();
        public final JButton updateButton = new JButton("Update Size");
        public final JButton nextButton = new JButton("Next Packet");
        public final JButton previousButton = new JButton("Previous Packet");
        public final JButton printButton = new JButton("Print Sizes");
        public final JLabel messageLabel = new JLabel();

        private PacketBlock block;

        public PacketFrame() {
            super("Packet Size Tool");
            setResizable(false);
            setPreferredSize(new Dimension(500, 281));
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            getContentPane().setLayout(null);
            addComponents();
            update(true);
            pack();
            setLocationRelativeTo(null);
            setVisible(true);

        }

        public PacketFrame update(boolean next) {
            if(block != null) {
                if(next) {
                    for(int opcode = block.opcode + 1; opcode <= PACKETS.length; opcode++) {
                        if(opcode >= PACKETS.length) {
                            block = null;
                            break;
                        }
                        PacketBlock nextBlock = PACKETS[opcode];
                        if(nextBlock != null) {
                            block = nextBlock;
                            break;
                        }
                    }
                } else {
                    for(int opcode = block.opcode - 1; opcode >= -1; opcode--) {
                        if(opcode < 0) {
                            block = null;
                            break;
                        }
                        PacketBlock prevBlock = PACKETS[opcode];
                        if(prevBlock != null) {
                            block = prevBlock;
                            break;
                        }
                    }
                }
            }
            if(block == null) {
                if(next) {
                    for(int opcode = 0; opcode < PACKETS.length; opcode++) {
                        PacketBlock nextBlock = PACKETS[opcode];
                        if(nextBlock != null) {
                            block = nextBlock;
                            break;
                        }
                    }
                } else {
                    for(int opcode = PACKETS.length - 1; opcode >= 0; opcode--) {
                        PacketBlock prevBlock = PACKETS[opcode];
                        if(prevBlock != null) {
                            block = prevBlock;
                            break;
                        }
                    }
                }
            }
            opcodeLabel.setText("Client Packet: " + block.opcode);
            estimatedLabel.setText("Estimated Size: " + (block.size == UNDETERMINED_SIZE ? "N/A" : block.size));
            codeArea.setText("");
            for(String s : block.lines) {
                codeArea.append(s);
                codeArea.append("\n");
            }
            codeArea.setCaretPosition(0);

            int setSize = SIZES[block.opcode];
            sizeField.setText(setSize == UNDETERMINED_SIZE ? "" : String.valueOf(setSize));
            messageLabel.setText("Last Update: Never");
            return this;
        }

        private void addComponents() {
            Border paddingBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
            Border border = BorderFactory.createLineBorder(Color.lightGray);
            Font f = opcodeLabel.getFont();

            opcodeLabel.setBounds(10, 10, 324, 23);
            opcodeLabel.setBorder(BorderFactory.createCompoundBorder(border, paddingBorder));
            opcodeLabel.setFont(new Font(f.getName(), Font.BOLD, 13));
            opcodeLabel.setText("Client Packet: N/A");
            getContentPane().add(opcodeLabel);

            estimatedLabel.setBounds(345, 10, 130, 23);
            estimatedLabel.setBorder(BorderFactory.createCompoundBorder(border,paddingBorder));
            estimatedLabel.setFont(new Font(f.getName(), Font.ITALIC, 12));
            estimatedLabel.setText("Estimated Size: " + "N/A");
            getContentPane().add(estimatedLabel);

            codeArea.setEditable(false);
            codeArea.setText("No code to display.");
            codeArea.setMargin(new Insets(5, 5, 5, 5));
            codeAreaPane.setBounds(10, 40, 324, 185);
            codeAreaPane.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
            getContentPane().add(codeAreaPane);

            sizeField.setBounds(344, 40, 130, 45);
            sizeField.setBorder(new CompoundBorder(BorderFactory.createTitledBorder("Custom Size"), sizeField.getBorder()));
            sizeField.setMargin(new Insets(0, 2, 0, 2));
            getContentPane().add(sizeField);

            updateButton.setBounds(344, 90, 130, 30);
            updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        SIZES[block.opcode] = Integer.valueOf(sizeField.getText().trim());
                        messageLabel.setText("Successfully updated size to " + SIZES[block.opcode] + " at " + DATE_FORMAT.format(Calendar.getInstance().getTime()));
                    } catch(NumberFormatException fe) {
                        messageLabel.setText("Warning: Update failed, please enter a valid number!");
                    }
                }
            });
            getContentPane().add(updateButton);

            nextButton.setBounds(344, 125, 130, 30);
            nextButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    update(true);
                }
            });
            getContentPane().add(nextButton);

            previousButton.setBounds(344, 160, 130, 30);
            previousButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    update(false);
                }
            });
            getContentPane().add(previousButton);

            printButton.setBounds(344, 195, 130, 30);
            printButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for(int i = 0; i < SIZES.length; i++) {
                        int size = SIZES[i];
                        if(size == NON_EXISTENT_SIZE)
                            continue;
                        System.out.println("SIZES[" + i + "] = " + size + ";");
                    }
                    System.out.println();
                }
            });
            getContentPane().add(printButton);

            messageLabel.setBounds(10, 230, 480, 16);
            messageLabel.setFont(new Font(f.getName(), Font.PLAIN, 11));
            getContentPane().add(messageLabel);
        }

    }

    public static void main(String[] args) throws Exception {
        for(int i = 0; i < SIZES.length; i++)
            SIZES[i] = NON_EXISTENT_SIZE;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + File.separator + "Desktop"));
        fileChooser.setDialogTitle("Select Source Directory");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(new JFrame());
        if(result == JFileChooser.APPROVE_OPTION) {
            start(fileChooser.getSelectedFile());
            new PacketFrame();
            return;
        }
        throw new IOException("Failed to select directory: " + result);
    }

    private static void start(File dir) throws IOException {
        if(!dir.isDirectory())
            throw new IOException("Selected file is not a directory!");
        File[] files = dir.listFiles();
        if(files == null)
            throw new IOException("No files found in directory!");
        for(File file : files) {
            if(!file.getName().endsWith(".java"))
                continue;
            read(file);
        }
    }

    private static void read(File file) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        int opcode = -1;
        ArrayList<String> callers = new ArrayList<>();
        int blockStart = -1;
        int innerBlocks = 0;
        ArrayList<String> blockLines = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while((line = br.readLine()) != null) {
                String[] s = line.split("} else");
                if(s.length == 1) {
                    lines.add(line);
                    continue;
                }
                if(s.length == 2) {
                    lines.add(s[0] + "}");
                    lines.add("else" + s[1]);
                    continue;
                }
                throw new IOException("Invalid break! | " + line);
            }
        }
        for(int offset = 0; offset < lines.size(); offset++) {
            String line = lines.get(offset);
            if(opcode == -1) {
                if(line.contains("{")) {
                    blockStart = offset;
                    continue;
                }
                if(line.contains(IDENTIFIER)) {
                    String[] s = line.split(IDENTIFIER + "\\(");
                    opcode = Integer.valueOf(s[1].split("\\)")[0]);
                    String caller = s[0].trim();
                    if(!caller.endsWith("."))
                        throw new IOException("Invalid caller (" + caller + ") on line (" + line.trim() + ") for opcode (" + opcode + ")");
                    callers.add(caller.substring(0, caller.length() - 1));
                    SIZES[opcode] = UNDETERMINED_SIZE;
                }
            } else {
                if(line.contains(" = " + callers.get(0))) {
                    String caller = line.split(" = ")[0].trim().split(" ")[1];
                    callers.add(caller);
                }
                if(line.contains("{"))
                    innerBlocks++;
                if(line.contains("}"))
                    innerBlocks--;
                if(innerBlocks == -1) {
                    int trim = -1;
                    for(int i = blockStart; i <= offset; i++) {
                        String s = lines.get(i);
                        if(trim == -1) {
                            trim = 0;
                            char[] cArray = s.toCharArray();
                            for(char c : cArray) {
                                if(c != ' ') {
                                    break;
                                }
                                trim++;
                            }
                        }
                        if(trim > 0) {
                            int toTrim = 0;
                            char[] cArray = s.toCharArray();
                            for(char c : cArray) {
                                if(c != ' ') {
                                    break;
                                }
                                if(++toTrim >= trim)
                                    break;
                            }
                            s = s.substring(toTrim, s.length());
                        }
                        blockLines.add(s);
                    }
                    PacketBlock oldBlock = PACKETS[opcode];
                    PacketBlock newBlock = new PacketBlock(opcode, blockLines, callers);
                    if(oldBlock != null && oldBlock.size != newBlock.size) {
                        if(oldBlock.size == -1 && newBlock.size == 1) {
                            newBlock = oldBlock;
                        } else if(oldBlock.size == 1 && newBlock.size == -1) {
                            /* ignore */
                        } else {
                            System.err.println("Duplicate sizes (" + oldBlock.size + ", " + newBlock.size + ") for opcode (" + opcode + ") do not match!");
                            newBlock.size = UNDETERMINED_SIZE;
                        }
                    }
                    PACKETS[opcode] = newBlock;
                    /**
                     * Reset
                     */
                    opcode = -1;
                    callers.clear();
                    blockStart = -1;
                    innerBlocks = 0;
                    blockLines.clear();
                }
            }

        }
    }

    private static final class PacketBlock {
        public final int opcode;
        public final String[] lines;
        public final String[] callers;
        public int size;
        private boolean writesVar;

        public PacketBlock(int opcode, ArrayList<String> lines, ArrayList<String> callers) {
            this.opcode = opcode;
            this.lines = lines.toArray(new String[lines.size()]);
            this.callers = callers.toArray(new String[callers.size()]);
            this.size = findSize();
            int identifierCount = 0;
            for(String line : lines) {
                if(line.contains(IDENTIFIER))
                    identifierCount++;
            }
            if(identifierCount != 1)
                throw new RuntimeException("Invalid Identifier Count!");
            SIZES[opcode] = size;
        }

        private int findSize() {
            int size = 0;
            boolean suspiciousCall = false;
            ArrayList<String> streamLines = new ArrayList<>();
            for(String line : lines) {
                String caller = getCaller(line);
                if(caller == null)
                    continue;
                line = line.trim();
                if(!line.startsWith(caller)) {
                    String s = line.replace(" ", "");
                    if(s.contains("(" + caller + ")")) //someMethod(caller);
                        suspiciousCall = true;
                    if(s.contains("(" + caller + ",")) //someMethod(caller, someValue);
                        suspiciousCall = true;
                    if(s.contains("," + caller + ")")) //someMethod(someValue, caller);
                        suspiciousCall = true;
                    if(s.contains("," + caller + ",")) //someMethod(someValue1, caller, someValue2);
                        suspiciousCall = true;
                    continue;
                }
                if(line.contains(caller + "." + IDENTIFIER))
                    continue;
                streamLines.add(line);
                int setLength = getSize(line, caller, "set");
                if(setLength != 0) {
                    if(setLength < 0)
                        throw new RuntimeException("Invalid Set Length (" + setLength + "): " + line);
                    return -setLength;
                }
                int startSize = size;
                size += getSize(line, caller, "add", "write");
                if(size == startSize)
                    System.err.println("Unknown caller method: " + line);
            }
            if(writesVar) {
                String line = streamLines.get(0);
                String caller = getCaller(line);
                int varLength = getSize(line, caller, "add", "write");
                if(varLength < 0)
                    throw new RuntimeException("Invalid Var Length (" + varLength + "): " + line);
                return -varLength;
            }
            return suspiciousCall ? UNDETERMINED_SIZE : size;
        }

        private String getCaller(String line) {
            for(String caller : callers) {
                if(line.contains(caller))
                    return caller;
            }
            return null;
        }

        private int getSize(String line, String caller, String... names) {
            int size = 0;
            String[] s;
            for(String name : names) {
                s = line.split(caller + "." + name);
                for(String call : s) {
                    int foundSize = getSize(call);
                    if(foundSize < 0)
                        writesVar = true;
                    size += foundSize;
                }
            }
            return size;
        }

        private static int getSize(String line) {
            line = line.toLowerCase();
            if(line.startsWith("le"))
                line = line.substring(2);
            if(line.startsWith("byte"))
                return 1;
            if(line.startsWith("short"))
                return 2;
            if(line.startsWith("tri") || line.startsWith("24bit") || line.startsWith("medium"))
                return 3;
            if(line.startsWith("int"))
                return 4;
            if(line.startsWith("long"))
                return 8;
            if(line.startsWith("string") || line.startsWith("jagstring"))
                return -1;
            if(line.startsWith("smart"))
                return Integer.MIN_VALUE;
            return 0;
        }
    }

}
