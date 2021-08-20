package cloud.thoughtspotstaging.champagne.testing;

public class Test {
    public static void main(String[] args) {
        String text = "{\"userContent\":{\"userPreferences\":{\"notifyOnShare\":true,\"showWalkMe\":true,\"analystOnboardingComplete\":false},\"userProperties\":{\"mail\":\"srikanth.jandhyala@thoughtspot.com\"},\"userActivityProto\":{\"first_login\":-1,\"welcome_email_sent\":false}},\"state\":\"ACTIVE\",\"assignedGroups\":[\"b25ee394-9d13-49e3-9385-cd97f5b253b4\"],\"inheritedGroups\":[\"b25ee394-9d13-49e3-9385-cd97f5b253b4\"],\"privileges\":[],\"type\":\"LOCAL_USER\",\"parenttype\":\"USER\",\"visibility\":\"DEFAULT\",\"tenantId\":\"982d6da9-9cd1-479e-b9a6-35aa05f9282a\",\"displayName\":\"Srikanth\",\"header\":{\"id\":\"d93b7f5e-cc06-4b4f-94fe-a7d8fe5ae715\",\"indexVersion\":0,\"generationNum\":0,\"name\":\"Srikanth\",\"author\":\"59481331-ee53-42be-a548-bd87be6ddd4a\",\"created\":1628242140873,\"modified\":1628242140873,\"modifiedBy\":\"59481331-ee53-42be-a548-bd87be6ddd4a\",\"owner\":\"d93b7f5e-cc06-4b4f-94fe-a7d8fe5ae715\",\"tags\":[],\"isExternal\":false,\"isDeprecated\":false},\"complete\":true,\"incompleteDetail\":[],\"isSuperUser\":false,\"isSystemPrincipal\":false}";
        String userId = text.substring(text.indexOf("\"id\":")+5, text.indexOf(",\"indexVersion\"")).replace("\"", "");
        System.out.println(userId);

        //.toJson(sb, String.class)
    }
}
