/*
 * Copyright (c) 2015 Jonas Kalderstam
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.cowboyprogrammer.org;

import org.cowboyprogrammer.org.parser.RegexParser;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

public class OrgFileTest {
    private static final String TESTFILEPATH = "test.org";

    private static void print(final Collection<String> strings) {
        for (final String s : strings) {
            System.out.print(s);
            System.out.print(" ");
        }
        System.out.println("");
    }

    private static void print(final String... strings) {
        for (final String s : strings) {
            System.out.print(s);
            System.out.print(" ");
        }
        System.out.println("");
    }

    @Test
    public void testfile() throws Exception {
        final OrgFile root = OrgFile.createFromFile(new RegexParser(), getFile(TESTFILEPATH));
        print("\n\n");
        print(root.treeToString());

        OrgNode leaf = root;
        while (leaf != null) {
            print(leaf.getAllTags());
            if (leaf.getSubNodes().isEmpty()) {
                leaf = null;
            } else {
                leaf = leaf.getSubNodes().get(0);
            }
        }

        writeToFile(File.createTempFile("test-out", ".org"), root);
    }

    private File getFile(String path) throws Exception {
        return new File(getClass().getResource(path).toURI());
    }

    private void writeToFile(File file, OrgFile root) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        root.writeToBuffer(bw);
        bw.close();
    }
}
