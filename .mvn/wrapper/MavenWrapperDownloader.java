/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.util.Properties;

public class MavenWrapperDownloader {

    private static final String WRAPPER_VERSION = "3.1.0";
    private static final String MAVEN_USER_HOME = System.getProperty("user.home");
    private static final String MAVEN_REPO_URL = System.getProperty(
            "maven.repo.remote",
            "https://repo.maven.apache.org/maven2");
    private static final String MAVEN_DOWNLOAD_EXTENSION = ".jar";

    public static void main(String[] args) {
        System.out.println("- Downloading started...");
        File baseDirectory = new File(args[0]);
        String version = args[1];
        String artifactId = args[2];
        String extension = args[3];

        try {
            downloadFileFromURL(
                    MAVEN_REPO_URL + "/"
                            + artifactId.replace(".", "/")
                            + "/" + WRAPPER_VERSION
                            + "/" + artifactId + "-" + WRAPPER_VERSION + extension,
                    new File(baseDirectory, artifactId + "-" + version + extension));
            System.out.println("");
            System.out.println("- Download finished");
        } catch (IOException e) {
            System.err.println("- Error downloading: " + e.getMessage());
        }
    }

    private static void downloadFileFromURL(String urlString, File destination) throws IOException {
        URL website = new URL(urlString);
        ReadableByteChannel rbc;
        rbc = Channels.newChannel(website.openStream());
        FileOutputStream fos = new FileOutputStream(destination);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }
}