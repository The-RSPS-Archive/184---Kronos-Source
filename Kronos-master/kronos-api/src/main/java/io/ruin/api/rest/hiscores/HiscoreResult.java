package io.ruin.api.rest.hiscores;

//import io.ruin.api.rest.KronosRest;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//
///**
// * @author ReverendDread on 6/14/2020
// * https://www.rune-server.ee/members/reverenddread/
// * @project Kronos
// */
//@RequiredArgsConstructor
//@Getter
//public class HiscoreResult {
//
//    private final String name;
//
//    public String build() {
//        StringBuilder sb = new StringBuilder();
//        KronosRest.database.executeAwait(connection -> {
//            PreparedStatement statement = connection.prepareStatement("SELECT * FROM highscores WHERE username=?");
//            statement.setString(1, name);
//            ResultSet rs = statement.executeQuery();
//            while (rs.next()) {
//                long overall_exp = rs.getLong("overall_exp");
//                int overall_level = rs.getInt("overall_level");
//                sb.append("0," + overall_level + "," + overall_exp + "\n");
//                int skills_count = 23;
//                int startColumn = 6;
//                for (int i = startColumn; i < (startColumn + skills_count * 2); i += 2) {
//                    int skill_exp = rs.getInt(i);
//                    int skill_level = rs.getInt(i + 1);
//                    sb.append("0," + skill_level + "," + skill_exp + "\n");
//                }
//                int misc_count = 10;
//                for (int i = startColumn + skills_count; i < (startColumn + skills_count + misc_count); i++) {
//                    sb.append("0,0" + (i != (startColumn + skills_count + misc_count - 1) ? "\n" : ""));
//                }
//            }
//        });
//        return sb.toString();
//    }
//
//}
