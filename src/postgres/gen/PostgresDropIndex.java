package postgres.gen;

import java.util.List;

import lama.Query;
import lama.QueryAdapter;
import lama.Randomly;
import lama.sqlite3.gen.SQLite3Common;
import postgres.PostgresSchema.PostgresIndex;

public class PostgresDropIndex {

	public static Query create(List<PostgresIndex> indexes) {
		StringBuilder sb = new StringBuilder();
		sb.append("DROP INDEX ");
		if (Randomly.getBoolean() || indexes.isEmpty()) {
			sb.append("IF EXISTS ");
			for (int i = 0; i < Randomly.smallNumber() + 1; i++) {
				if (i != 0) {
					sb.append(", ");
				}
				if (indexes.isEmpty() || Randomly.getBoolean()) {
					sb.append(SQLite3Common.createIndexName(Randomly.smallNumber()));
				} else {
					sb.append(Randomly.fromList(indexes).getIndexName());
				}
			}
		} else {
			for (int i = 0; i < Randomly.smallNumber() + 1; i++) {
				if (i != 0) {
					sb.append(", ");
				}
				sb.append(Randomly.fromList(indexes).getIndexName());
			}
		}
		if (Randomly.getBoolean()) {
			sb.append(" ");
			sb.append(Randomly.fromOptions("CASCADE", "RESTRICT"));
		}
		return new QueryAdapter(sb.toString());
	}

}