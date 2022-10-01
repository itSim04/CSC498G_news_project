package com.csc498g.newsfeed;

enum TABLE_COLUMNS {

    AUTHOR("author", 0), OWNER("owner", 1), HEADLINE("headline", 2), DESCRIPTION("description", 3), PUBLISHED_AT("published_at", 4), LOCATION("location", 5);

    final String label;
    final int index;

    TABLE_COLUMNS(String label, int index) {
        this.label = label;
        this.index = index;
    }


}
