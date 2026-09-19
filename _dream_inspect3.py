import sqlite3, json

db = r"C:\Users\.MSI\.local\share\mimocode\mimocode.db"
conn = sqlite3.connect(db)
cur = conn.cursor()

# Check what the checkpoint-writer session did
print("=== Checkpoint writer ses_082294026ffenH3be3bTaapssu ===")
cur.execute("""
SELECT m.id, json_extract(m.data, '$.role') as role, 
       json_extract(p.data, '$.type') as part_type,
       json_extract(p.data, '$.tool') as tool,
       substr(json_extract(p.data, '$.text'), 1, 500) as text
FROM message m
JOIN part p ON p.message_id = m.id
WHERE m.session_id = 'ses_082294026ffenH3be3bTaapssu'
ORDER BY m.time_created, p.time_created
""")
for r in cur.fetchall():
    role = r[1]
    ptype = r[2] or ""
    tool = r[3] or ""
    text = r[4] or ""
    if ptype == "text" and text.strip():
        print(f"  [{role}] {text[:400]}")
    elif ptype == "tool":
        print(f"  [{role}] TOOL:{tool}")

# Now let's search for durable user directives in recent sessions
print("\n=== User directives search (all LiveScores sessions) ===")
live_sessions = [
    'ses_0822a097bffes29w3YZmLv05t4',
    'ses_08b5a5175ffewRjgbHVLZryUaB',
    'ses_0b2bdf024fferA3x0fVyn14lX6',
]
for sid in live_sessions:
    cur.execute("""
    SELECT substr(json_extract(p.data, '$.text'), 1, 600) as t
    FROM message m JOIN part p ON p.message_id = m.id
    WHERE m.session_id = ?
    AND json_extract(m.data, '$.role') = 'user'
    AND json_extract(p.data, '$.type') = 'text'
    ORDER BY m.time_created
    """, (sid,))
    rows = cur.fetchall()
    if rows:
        print(f"\n  Session {sid}:")
        for r in rows:
            if r[0] and r[0].strip():
                print(f"    {r[0][:400]}")

# Also check what edits were applied in the lineup fix session
print("\n=== Edits applied in ses_0822a097bffes29w3YZmLv05t4 ===")
cur.execute("""
SELECT substr(json_extract(p.data, '$.state.input'), 1, 1200) as inp
FROM message m
JOIN part p ON p.message_id = m.id
WHERE m.session_id = 'ses_0822a097bffes29w3YZmLv05t4'
AND json_extract(m.data, '$.role') = 'assistant'
AND json_extract(p.data, '$.tool') = 'edit'
ORDER BY m.time_created
""")
for r in cur.fetchall():
    if r[0]:
        try:
            inp = json.loads(r[0] + '"}}')  # won't parse, just print raw
        except:
            pass
        print(f"  {r[0][:800]}")
        print()

conn.close()
