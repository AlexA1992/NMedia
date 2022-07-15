package ru.netology.nmedia.DB

object PostsTable {
    const val NAME = "posts"

    val DDL = """
        CREATE TABLE $NAME (
        ${Column.ID.columnName} INTEGER PRIMARY KEY AUTOINCREMENT,
        ${Column.LIKED.columnName} TEXT NOT NULL,
        ${Column.LIKED.columnName} TEXT NOT NULL,
        ${Column.LIKEDBYME.columnName} BOOLEAN NOT NULL,
        ${Column.CONTENT.columnName} TEXT NOT NULL,
        ${Column.DATE.columnName} TEXT NOT NULL,
        ${Column.AUTHOR.columnName} TEXT NOT NULL,
        ${Column.REPOSTSQ.columnName} INTEGER NOT NULL,
        ${Column.EDITED.columnName} BOOLEAN NOT NULL,
        ${Column.VIDEO.columnName} TEXT NOT NULL
        );
        """.trimIndent()

val ALL_COLUMNS_NAMES = Column.values().map { it.columnName }.toTypedArray()

    enum class Column(val columnName: String) {
        ID("id"),
        LIKED("liked"),
        LIKEDBYME("likedbyme"),
        CONTENT("content"),
        DATE("date"),
        AUTHOR("author"),
        REPOSTSQ("repostedq"),
        EDITED("edited"),
        VIDEO("video")
    }
}