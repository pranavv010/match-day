import sqlite3, json

db = r"C:\Users\.MSI\.local\share\mimocode\mimocode.db"
conn = sqlite3.connect(db)
cur = conn.cursor()

# Get assistant text outputs and tool details from ses_0822a097bffes29w3YZmLv05t4
print("=== Full assistant parts from ses_0822a097bffes29w3YZmLv05t4 ===")
cur.execute("""
SELECT m.id, json_extract(p.data, '$.type') as part_type, 
       json_extract(p.data, '$.tool') as tool,
       json_extract(p.data, '$.text') as text,
       substr(json_extract(p.data, '$.state.input'), 1, 500) as tool_input,
       substr(json_extract(p.data, '$.state.output'), 1, 500) as tool_output
FROM message m
JOIN part p ON p.message_id = m.id
WHERE m.session_id = 'ses_0822a097bffes29w3YZmLv05t4'
  AND json_extract(m.data, '$.role') = 'assistant'
ORDER BY m.time_created, p.time_created
""")
for r in cur.fetchall():
    part_type = r[1] or "unknown"
    tool = r[2] or ""
    text = r[3] or ""
    tool_input = r[4] or ""
    tool_output = r[5] or ""
    
    if part_type == "text" and text.strip():
        print(f"  [TEXT] {text[:500]}")
    elif part_type == "tool" and tool:
        print(f"  [TOOL: {tool}]")
        if tool_input:
            print(f"    input: {tool_input[:400]}")
        if tool_output:
            print(f"    output: {tool_output[:400]}")
    elif part_type in ("step-start", "step-finish"):
        tokens = ""
        if part_type == "step-finish":
            tokens_data = r[3] or ""
            tokens = f" tokens:{tokens_data}"
        print(f"  [{part_type.upper()}{tokens}]")
    print()

# Also check if there's a newer session (ses_082294026ffenH3be3bTaapssu) that the checkpoint-writer was working on
print("\n=== Messages from checkpoint-writer session ses_082294026ffenH3be3bTaapssu ===")
cur.execute("""
SELECT m.id, json_extract(m.data, '$.role') as role, substr(json_extract(p.data, '$.text'), 1, 300) as text
FROM message m
JOIN part p ON p.message_id = m.id
WHERE m.session_id = 'ses_082294026ffenH3be3bTaapssu'
  AND json_extract(p.data, '$.type') = 'text'
ORDER BY m.time_created
""")
for r in cur.fetchall():
    print(f"  [{r[1]}] {r[2]}")

# Check notes.md for current session
print("\n=== notes.md for current dream session ===")
try:
    cur.execute("SELECT 1")
except:
    pass

conn.close()
