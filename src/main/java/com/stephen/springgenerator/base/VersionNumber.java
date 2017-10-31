package com.stephen.springgenerator.base;


import com.stephen.springgenerator.util.RegexUtils;

public class VersionNumber implements Comparable<VersionNumber> {

    private final int part1;
    private final int part2;
    // Maybe null
    private final Integer part3;
    private final String suffix;

    private VersionNumber(int part1, int part2, Integer part3, String suffix) {
        this.part1 = part1;
        this.part2 = part2;
        this.part3 = part3;
        this.suffix = suffix;
    }

    public static VersionNumber parse(String s) {
        String[] parts = s.split("\\.");
        int length = parts.length;

        if (length < 2) {
            throw FormatException.forInputString(s);
        }

        try {
            switch (length) {
                case 2:
                    return new VersionNumber(Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]), null, null);
                case 3:
                    String part3 = parts[2];
                    if (RegexUtils.isNumeric(part3)) {
                        return new VersionNumber(Integer.parseInt(parts[0]),
                                Integer.parseInt(parts[1]),
                                Integer.parseInt(part3), null);
                    } else {
                        return new VersionNumber(Integer.parseInt(parts[0]),
                                Integer.parseInt(parts[1]), null, part3);
                    }
                case 4:
                    return new VersionNumber(Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]),
                            Integer.parseInt(parts[2]), parts[3]);
                default:
                    return null;
            }
        } catch (NumberFormatException e) {
            throw FormatException.forInputString(s);
        }
    }

    @Override
    public int compareTo(VersionNumber o) {
        if (part1 != o.part1) {
            return part1 - o.part1;
        } else if (part2 != o.part2) {
            return part2 - o.part2;
        } else {
            // 4种组合情况
            if (part3 == null && o.part3 != null) {
                return -1;
            } else if (part3 != null && o.part3 == null) {
                return 1;
            } else if (part3 == null && o.part3 == null) {
                return 0;
            } else {
                return part3 - o.part3;
            }
        }
    }

    public String getSuffix() {
        return suffix;
    }

    public int getPart1() {
        return part1;
    }

    public int getPart2() {
        return part2;
    }

    public int getPart3() {
        return part3;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(part1);
        sb.append(".");
        sb.append(part2);

        if (part3 != null) {
            sb.append(".");
            sb.append(part3);
        }

        if (suffix != null) {
            sb.append(".");
            sb.append(suffix);
        }

        return sb.toString();
    }
}
