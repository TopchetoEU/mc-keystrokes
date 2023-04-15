package me.topchetoeu.keystrokes.engine;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class Buttons {
    private HashSet<Button> buttons = new HashSet<>();
    public final File confFile;

    public void register(Button btn) {
        buttons.add(btn);
    }

    public Set<Button> get() {
        return Collections.unmodifiableSet(buttons);
    }

    public void read() throws IOException {
        var styles = new ArrayList<ButtonStyle>();
        var f = new FileInputStream(confFile);
        var str = new DataInputStream(f);
        int n = str.readInt();

        for (int i = 0; i < n; i++) {
            styles.add(new ButtonStyle(f));
        }

        n = str.readInt();

        buttons.clear();

        for (int i = 0; i < n; i++) {
            buttons.add(new Button(styles, f));
        }

        f.close();
    }
    public void write() throws IOException {
        var res = new ArrayList<ButtonStyle>();
        var f = new FileOutputStream(confFile);
        for (var btn : buttons) {
            if (!res.contains(btn.style)) {
                res.add(btn.style);
                btn.style.write(f);
            }
        }
        for (var btn : buttons) {
            btn.write(res, f);
        }
        f.close();
    }
    
    public Buttons(File confFile) {
        this.confFile = confFile;
    }
}
