import sqlite3, json, sys

db = r"C:\Users\.MSI\.local\share\mimocode\mimocode.db"
conn = sqlite3.connect(db)
cur = conn.cursor()

# Get all LiveScores sessions (non-checkpoint-writer)
cur.execute("SELECT id, title, time_created FROM session WHERE directory LIKE '%LiveScores%' AND title NOT LIKE 'checkpoint-writer%' ORDER BY time_created DESC LIMIT 10")
print("=== LiveScores sessions ===")
for r in cur.fetchall():
    print(f"  {r[0]} | {r[2]} | {r[1]}")

# Get messages from recent session ses_0822a097bffes29w3YZmLv05t4
print("\n=== Messages from ses_0822a097bffes29w3YZmLv05t4 ===")
cur.execute("SELECT id, agent_id, json_extract(data, '$.role') as role, time_created FROM message WHERE session_id = 'ses_0822a097bffes29w3YZmLv05t4' ORDER BY time_created")
for r in cur.fetchall():
    print(f"  msg:{r[0]} agent:{r[1]} role:{r[2]} time:{r[3]}")

# Get parts for user messages in that session to find what user asked
print("\n=== User messages in ses_0822a097bffes29w3YZmLv05t4 ===")
cur.execute("""
SELECT m.id, p.id as part_id, substr(json_extract(p.data, '$.text'), 1, 500) as text_preview
FROM message m
JOIN part p ON p.message_id = m.id
WHERE m.session_id = 'ses_0822a097bffes29w3YZmLv05t4'
  AND json_extract(m.data, '$.role') = 'user'
  AND json_extract(p.data, '$.type') = 'text'
ORDER BY m.time_created
""")
for r in cur.fetchall():
    print(f"  msg:{r[0]} | {r[2]}")

# Get assistant tool calls from that session  
print("\n=== Assistant tool calls in ses_0822a097bffes29w3YZmLv05t4 ===")
cur.execute("""
SELECT m.id, json_extract(p.data, '$.tool') as tool, substr(p.data, 1, 600) as preview
FROM message m
JOIN part p ON p.message_id = m.id
WHERE m.session_id = 'ses_0822a097bffes29w3YZmLv05t4'
  AND json_extract(m.data, '$.role') = 'assistant'
  AND json_extract(p.data, '$.type') = 'tool'
ORDER BY m.time_created, p.time_created
""")
for r in cur.fetchall():
    tool = r[1] or "unknown"
    print(f"  msg:{r[0]} tool:{tool} preview:{r[2][:200]}")

# Now check the last checkpoint for this project
print("\n=== Last checkpoint in memory for this project ===")
cur.execute("SELECT id, title, time_created FROM session WHERE directory LIKE '%LiveScores%' AND title LIKE 'checkpoint-writer%' ORDER BY time_created DESC LIMIT 3")
for r in cur.fetchall():
    print(f"  {r[0]} | {r[2]} | {r[1]}")

conn.close()
