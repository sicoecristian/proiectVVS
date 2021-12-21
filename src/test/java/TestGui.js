
function main() {
    startApplication("GuiMain.class");
    clickButton(waitForObject(names.vVSWebServerControllerStartJButton));
    clickButton(waitForObject(names.messageOKJButton));
    clickButton(waitForObject(names.vVSWebServerControllerChangePortJButton));
    clickButton(waitForObject(names.messageOKJButton));
    clickButton(waitForObject(names.vVSWebServerControllerStopJButton));
    clickButton(waitForObject(names.messageOKJButton));
    clickButton(waitForObject(names.vVSWebServerControllerChangePortJButton));
    clickButton(waitForObject(names.messageOKJButton));
    mouseClick(waitForObject(names.vVSWebServerControllerJTextField), 65, 11, 0, Button.Button1);
    type(waitForObject(names.vVSWebServerControllerJTextField), "abcd");
    clickButton(waitForObject(names.vVSWebServerControllerChangePortJButton));
    clickButton(waitForObject(names.messageOKJButton));
    mouseDrag(waitForObject(names.vVSWebServerControllerJTextField), 87, 14, -101, -3, Modifier.None, Button.Button1);
    type(waitForObject(names.vVSWebServerControllerJTextField), "8081");
    clickButton(waitForObject(names.vVSWebServerControllerChangePortJButton));
    clickButton(waitForObject(names.messageOKJButton));
    clickButton(waitForObject(names.vVSWebServerControllerMaintenanceJButton));
    clickButton(waitForObject(names.messageOKJButton));
    mouseClick(waitForObject(names.vVSWebServerControllerJTextField), 50, 6, 0, Button.Button1);
    type(waitForObject(names.vVSWebServerControllerJTextField), "<Backspace>");
    type(waitForObject(names.vVSWebServerControllerJTextField), "0");
    clickButton(waitForObject(names.vVSWebServerControllerChangePortJButton));
    clickButton(waitForObject(names.messageOKJButton));
    clickButton(waitForObject(names.vVSWebServerControllerStartJButton));
    clickButton(waitForObject(names.messageOKJButton));
    clickButton(waitForObject(names.vVSWebServerControllerChangeRootPathJButton));
    clickButton(waitForObject(names.messageOKJButton));
    clickButton(waitForObject(names.vVSWebServerControllerStopJButton));
    clickButton(waitForObject(names.messageOKJButton));
    clickButton(waitForObject(names.vVSWebServerControllerChangeRootPathJButton));
    clickButton(waitForObject(names.messageOKJButton));
    mouseClick(waitForObject(names.vVSWebServerControllerSrcMainJavaWebsiteJTextField), 62, 8, 0, Button.Button1);
    type(waitForObject(names.vVSWebServerControllerSrcMainJavaWebsiteJTextField), "test new toor");
    type(waitForObject(names.vVSWebServerControllerSrcMainJavaWebsiteJTextField), "<Backspace>");
    type(waitForObject(names.vVSWebServerControllerSrcMainJavaWebsiteJTextField), "<Backspace>");
    type(waitForObject(names.vVSWebServerControllerSrcMainJavaWebsiteJTextField), "<Backspace>");
    type(waitForObject(names.vVSWebServerControllerSrcMainJavaWebsiteJTextField), "<Backspace>");
    type(waitForObject(names.vVSWebServerControllerSrcMainJavaWebsiteJTextField), "rooth");
    clickButton(waitForObject(names.vVSWebServerControllerChangeRootPathJButton));
    clickButton(waitForObject(names.messageOKJButton));
}

