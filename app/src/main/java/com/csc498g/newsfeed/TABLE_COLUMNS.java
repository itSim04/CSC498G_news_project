package com.csc498g.newsfeed;

enum TABLE_COLUMNS {

    AUTHOR("author", 0), HEADLINE("headline", 1), DESCRIPTION("description", 2), PUBLISHED_AT("published_at", 3), LOCATION("location", 4);

    final String label;
    final int index;

    TABLE_COLUMNS(String label, int index) {
        this.label = label;
        this.index = index;
    }


}
