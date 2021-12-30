package com.voitenkov.sergei.repositories;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class IdGenerator {
    private static long lastUserId = 0L;

    public static Long generate() {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(Constants.FILE_PATH, "r")) {
            long fileLength = randomAccessFile.length() - 1;
            StringBuilder line = new StringBuilder();

            for (long filePointer = fileLength; filePointer != -1; filePointer--) {
                randomAccessFile.seek(filePointer);
                int readByte = randomAccessFile.readByte();

                if (readByte == 0xA) {
                    if (filePointer == fileLength) {
                        continue;
                    }
                    break;
                } else if (readByte == 0xD) {
                    if (filePointer == fileLength - 1) {
                        continue;
                    }
                    break;
                }
                line.append((char) readByte);
            }
            line.reverse();

            Pattern pattern = Pattern.compile("id=(?<id>[0-9]+)");
            Matcher matcher = pattern.matcher(line);

            while (matcher.find()) {
                lastUserId = Long.parseLong(matcher.group("id"));
            }

        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return lastUserId > 0 ? lastUserId + 1 : 1L;
    }
}