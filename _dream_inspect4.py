import sqlite3, json

db = r"C:\Users\.MSI\.local\share\mimocode\mimocode.db"
conn = sqlite3.connect(db)
cur = conn.cursor()

# Check what the checkpoint writer session wrote/edited
print("=== Checkpoint writer tool calls ===")
cur.execute("""
SELECT json_extract(p.data, '$.tool') as tool,
       json_extract(p.data, '$.state.input') as inp,
       json_extract(p.data, '$.state.output') as outp
FROM message m
JOIN part p ON p.message_id = m.id
WHERE m.session_id = 'ses_082294026ffenH3be3bTaapssu'
AND json_extract(p.data, '$.type') = 'tool'
ORDER BY m.time_created
""")
for r in cur.fetchall():
    tool = r[0] or ""
    inp = r[1] or ""
    outp = r[2] or ""
    print(f"\n  TOOL: {tool}")
    # For edit/write tools, show the input
    if tool in ("edit", "write"):
        try:
            d = json.loads(inp)
            if "file_path" in d:
                print(f"    file: {d['file_path']}")
            if "content" in d:
                print(f"    content (first 600): {d['content'][:600]}")
            if "new_string" in d:
                print(f"    new_string (first 600): {d['new_string'][:600]}")
            if "old_string" in d:
                print(f"    old_string (first 200): {d['old_string'][:200]}")
        except:
            print(f"    raw input: {inp[:500]}")

conn.close()
