/*
 * This file is generated by jOOQ.
 */
package net.runelite.client.database.data;


import javax.annotation.Generated;
import net.runelite.client.database.data.tables.Loottrackerevents;
import net.runelite.client.database.data.tables.Loottrackerlink;
import net.runelite.client.database.data.tables.Loottrackerloot;
import net.runelite.client.database.data.tables.User;
import net.runelite.client.database.data.tables.records.LoottrackereventsRecord;
import net.runelite.client.database.data.tables.records.LoottrackerlinkRecord;
import net.runelite.client.database.data.tables.records.LoottrackerlootRecord;
import net.runelite.client.database.data.tables.records.UserRecord;
import org.jooq.ForeignKey;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables of
 * the <code>PUBLIC</code> schema.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.11.12"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Keys
{

	// -------------------------------------------------------------------------
	// IDENTITY definitions
	// -------------------------------------------------------------------------


	// -------------------------------------------------------------------------
	// UNIQUE and PRIMARY KEY definitions
	// -------------------------------------------------------------------------

	public static final UniqueKey<LoottrackereventsRecord> PK_EVENTUNIQUEID = UniqueKeys0.PK_EVENTUNIQUEID;
	public static final UniqueKey<LoottrackerlinkRecord> PK_LOOTTRACKERLINK = UniqueKeys0.PK_LOOTTRACKERLINK;
	public static final UniqueKey<LoottrackerlootRecord> PK_LOOTUNIQUEID = UniqueKeys0.PK_LOOTUNIQUEID;
	public static final UniqueKey<UserRecord> PK_USER = UniqueKeys0.PK_USER;

	// -------------------------------------------------------------------------
	// FOREIGN KEY definitions
	// -------------------------------------------------------------------------

	public static final ForeignKey<LoottrackerlinkRecord, LoottrackereventsRecord> FK_LOOTTRACKEREVENT = ForeignKeys0.FK_LOOTTRACKEREVENT;
	public static final ForeignKey<LoottrackerlinkRecord, LoottrackerlootRecord> FK_LOOTTRACKERLOOT = ForeignKeys0.FK_LOOTTRACKERLOOT;
	public static final ForeignKey<LoottrackerlinkRecord, UserRecord> FK_USER = ForeignKeys0.FK_USER;

	// -------------------------------------------------------------------------
	// [#1459] distribute members to avoid static initialisers > 64kb
	// -------------------------------------------------------------------------

	private static class UniqueKeys0
	{
		public static final UniqueKey<LoottrackereventsRecord> PK_EVENTUNIQUEID = Internal.createUniqueKey(Loottrackerevents.LOOTTRACKEREVENTS, "PK_EVENTUNIQUEID", Loottrackerevents.LOOTTRACKEREVENTS.UNIQUEID);
		public static final UniqueKey<LoottrackerlinkRecord> PK_LOOTTRACKERLINK = Internal.createUniqueKey(Loottrackerlink.LOOTTRACKERLINK, "PK_LOOTTRACKERLINK", Loottrackerlink.LOOTTRACKERLINK.LINKUNIQUEID);
		public static final UniqueKey<LoottrackerlootRecord> PK_LOOTUNIQUEID = Internal.createUniqueKey(Loottrackerloot.LOOTTRACKERLOOT, "PK_LOOTUNIQUEID", Loottrackerloot.LOOTTRACKERLOOT.UNIQUEID);
		public static final UniqueKey<UserRecord> PK_USER = Internal.createUniqueKey(User.USER, "PK_USER", User.USER.UNIQUEID);
	}

	private static class ForeignKeys0
	{
		public static final ForeignKey<LoottrackerlinkRecord, LoottrackereventsRecord> FK_LOOTTRACKEREVENT = Internal.createForeignKey(net.runelite.client.database.data.Keys.PK_EVENTUNIQUEID, Loottrackerlink.LOOTTRACKERLINK, "FK_LOOTTRACKEREVENT", Loottrackerlink.LOOTTRACKERLINK.EVENTUNIQUEID);
		public static final ForeignKey<LoottrackerlinkRecord, LoottrackerlootRecord> FK_LOOTTRACKERLOOT = Internal.createForeignKey(net.runelite.client.database.data.Keys.PK_LOOTUNIQUEID, Loottrackerlink.LOOTTRACKERLINK, "FK_LOOTTRACKERLOOT", Loottrackerlink.LOOTTRACKERLINK.DROPUNIQUEID);
		public static final ForeignKey<LoottrackerlinkRecord, UserRecord> FK_USER = Internal.createForeignKey(net.runelite.client.database.data.Keys.PK_USER, Loottrackerlink.LOOTTRACKERLINK, "FK_USER", Loottrackerlink.LOOTTRACKERLINK.DROPUNIQUEID);
	}
}
