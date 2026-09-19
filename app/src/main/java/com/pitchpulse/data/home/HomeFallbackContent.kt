package com.pitchpulse.data.home

import com.pitchpulse.data.model.FootballQuote
import com.pitchpulse.data.model.QuizQuestion

object HomeFallbackContent {
    val defaultQuote = FootballQuote(
        text = "Football is the ballet of the masses.",
        author = "Dmitri Shostakovich"
    )

    val defaultFact =
        "Pelé is the only player to have won three FIFA World Cups (1958, 1962, 1970)."

    private val quotePool = listOf(
        defaultQuote,
        FootballQuote("Some people think football is a matter of life and death. I assure you, it's much more serious than that.", "Bill Shankly"),
        FootballQuote("I learned all about life with a ball at my feet.", "Ronaldinho"),
        FootballQuote("The ball is round, the game lasts ninety minutes, and everything else is pure theory.", "Sepp Herberger"),
        FootballQuote("Football is simple, but it is hard to play simple.", "Johan Cruyff"),
        FootballQuote("Talent without working hard is nothing.", "Cristiano Ronaldo"),
        FootballQuote("You have to fight to reach your dream. You have to sacrifice and work hard for it.", "Lionel Messi"),
        FootballQuote("The more difficult the victory, the greater the happiness in winning.", "Pelé"),
        FootballQuote("I am a product of my decisions, not my circumstances.", "Sir Alex Ferguson"),
        FootballQuote("Football without fans is nothing.", "Johan Cruyff"),
        FootballQuote("I don't believe in luck. I believe in hard work.", "Zlatan Ibrahimović"),
        FootballQuote("Victory is in the quality of the fight, not in the result.", "Paolo Maldini"),
        FootballQuote("A champion is someone who does not settle for what is given.", "Luis Suárez"),
        FootballQuote("The ball does not get tired. It is always there waiting.", "Andrés Iniesta"),
        FootballQuote("In football, the worst blindness is only seeing the goal.", "Eden Hazard"),
        FootballQuote("You are never too old to set another goal or to dream another dream.", "Cristiano Ronaldo"),
        FootballQuote("Respect all, fear none.", "Zinedine Zidane"),
        FootballQuote("I'm living a dream I never want to wake up from.", "Kylian Mbappé"),
        FootballQuote("Passion first, everything else follows.", "Carlo Ancelotti"),
        FootballQuote("Football is the purest form of democracy.", "Pep Guardiola"),
        FootballQuote("Hard work will always overcome talent when talent doesn't work hard.", "Jamie Vardy"),
        FootballQuote("The secret is to believe in your dreams.", "Paulo Dybala"),
        FootballQuote("It takes a whole team to win a match, not just one player.", "Luka Modrić"),
        FootballQuote("Life is like football — you need goals.", "Neymar Jr."),
        FootballQuote("A footballer's heart beats with every pass, tackle, and goal.", "Thierry Henry"),
        FootballQuote("Pressure is something you put on yourself.", "David Beckham"),
        FootballQuote("I never lost — I only ran out of time.", "Bobby Charlton"),
        FootballQuote("Doubt is only removed by action.", "Zinedine Zidane"),
        FootballQuote("The stadium is my cathedral.", "Diego Maradona"),
        FootballQuote("Champions keep playing until they get it right.", "Billie Jean King (on football)")
    )

    private val factPool = listOf(
        defaultFact,
        "The fastest red card in professional football was after 2 seconds (Lee Todd, 2000).",
        "Cristiano Ronaldo has scored in five different World Cups — a men's record.",
        "The original World Cup trophy was called the Jules Rimet Trophy.",
        "AC Milan and Inter Milan share the same stadium: San Siro / Giuseppe Meazza.",
        "The offside rule was introduced in 1863, though it has changed many times since.",
        "Lionel Messi has won more Ballon d'Or awards than any other player.",
        "The highest-scoring UEFA Champions League match ended 8-4 (Monaco vs Deportivo, 2003).",
        "Italy's 2006 World Cup squad featured four players born in Argentina.",
        "England's 1966 World Cup win remains their only men's title to date.",
        "Real Madrid won the first five European Cup tournaments from 1956 to 1960.",
        "The Premier League was formed in 1992 when clubs broke away from the Football League.",
        "Barcelona's Camp Nou is the largest stadium in Europe by capacity.",
        "Roger Milla scored four goals at the 1994 World Cup at age 42.",
        "The Bosnia and Herzegovina national team only debuted at a World Cup in 2014.",
        "Arsenal went the entire 2003-04 Premier League season unbeaten — the 'Invincibles'.",
        "The Panenka penalty was invented by Antonín Panenka in the 1976 Euro final.",
        "South American qualifiers for the World Cup are the longest of any confederation.",
        "The World Cup has been won by only eight nations in history.",
        "Zinedine Zidane was sent off in the 2006 World Cup final for headbutting Marco Materazzi.",
        "The 'Hand of God' goal was scored by Diego Maradona in the 1986 World Cup quarterfinal.",
        "Leicester City won the Premier League in 2016 at 5000-1 odds — the biggest shock in sports history.",
        "The European Cup was rebranded as the UEFA Champions League in 1992.",
        "Kenya has never qualified for a FIFA World Cup.",
        "The fastest goal in World Cup history was scored by Hakan Şükür after 11 seconds in 2002.",
        "Werder Bremen and Bayern Munich have contested the most DFB-Pokal finals.",
        "Boca Juniors and River Plate's Superclásico is one of the fiercest rivalries in world football.",
        "The FIFA World Cup trophy is made of 18-carat gold and weighs about 6.1 kilograms.",
        "Only three players have scored hat-tricks in a World Cup final: Geoff Hurst (1966) and Kylian Mbappé (2022).",
        "The oldest football club in the world is Sheffield F.C., founded in 1857.",
        "Iceland became the smallest nation by population to qualify for a World Cup in 2018.",
        "The ball used in the 1970 World Cup was called the Telstar.",
        "Miroslav Klose holds the record for most World Cup goals with 16.",
        "No team has successfully defended the UEFA European Championship since Spain in 2012.",
        "Liverpool's 4-0 comeback against Barcelona in 2019 is known as the 'Miracle at Anfield'.",
        "The Asian Football Confederation (AFC) was founded in 1954.",
        "Chelsea is the only London club to have won the UEFA Champions League.",
        "The first international football match was played between Scotland and England in 1872.",
        "Pelé scored 1,279 goals in 1,363 games over his career — a Guinness World Record.",
        "Benfica's Eusébio scored 9 goals in the 1966 World Cup, winning the Golden Boot.",
        "The Golden Ball is awarded to the best player at each World Cup.",
        "Gianluigi Buffon played 176 caps for Italy, the most for any Italian player.",
        "The Copa América is the oldest international continental football competition.",
        "FC Barcelona is the only European club to have won the sextuple (six trophies in a calendar year).",
        "Wrexham AFC is the oldest football club in Wales, founded in 1864.",
        "The football league system in England has over 140 separate leagues.",
        "A penalty shootout was first introduced in 1978 for World Cup matches.",
        "The most goals ever scored in a single World Cup match was 12 — Austria 7-5 Switzerland in 1954.",
        "Ronaldo Nazário won the World Cup in 1994 and 2002, but never won the UEFA Champions League.",
        "The name 'soccer' originated in England as slang for 'Association Football'."
    )

    private val tieredQuizPool: List<QuizQuestion> = listOf(
        // Level 1
        QuizQuestion("Which club won the first Premier League season (1992-93)?", listOf("Manchester United", "Blackburn Rovers", "Arsenal", "Leeds United"), 0, 1),
        QuizQuestion("Who is the all-time top scorer in UEFA European Championship history?", listOf("Cristiano Ronaldo", "Michel Platini", "Alan Shearer", "Antoine Griezmann"), 0, 1),
        QuizQuestion("Which country has won the most FIFA World Cup titles?", listOf("Brazil", "Germany", "Italy", "Argentina"), 0, 1),
        QuizQuestion("Which nation is credited with inventing modern football?", listOf("England", "Brazil", "Italy", "Scotland"), 0, 1),
        QuizQuestion("Who won the 2018 FIFA World Cup?", listOf("France", "Croatia", "Belgium", "England"), 0, 1),
        QuizQuestion("Which country hosted the 2014 FIFA World Cup?", listOf("Brazil", "South Africa", "Russia", "Qatar"), 0, 1),
        QuizQuestion("Who won the Ballon d'Or in 2023?", listOf("Lionel Messi", "Erling Haaland", "Kylian Mbappé", "Kevin De Bruyne"), 0, 1),
        QuizQuestion("Which club plays at Anfield?", listOf("Liverpool", "Everton", "Manchester City", "Tottenham"), 0, 1),
        QuizQuestion("The FIFA World Cup trophy was originally named after whom?", listOf("Jules Rimet", "Henri Delaunay", "Stanley Rous", "João Havelange"), 0, 1),
        QuizQuestion("Which position is the goalkeeper allowed to use their hands?", listOf("Inside the penalty area", "Anywhere on the pitch", "Inside the goal area only", "Only for goal kicks"), 0, 1),
        QuizQuestion("Who is known as 'The King of Football'?", listOf("Pelé", "Diego Maradona", "Lionel Messi", "Johan Cruyff"), 0, 1),
        QuizQuestion("Which country won the first ever World Cup in 1930?", listOf("Uruguay", "Argentina", "Brazil", "Italy"), 0, 1),
        QuizQuestion("Which European league is commonly called the Premier League?", listOf("England", "Spain", "Italy", "Germany"), 0, 1),
        QuizQuestion("What does the acronym FIFA stand for?", listOf("Fédération Internationale de Football Association", "Football International Federation Association", "Fédération Internationale de Football Amateur", "Fédération Internationale de Football Athlétique"), 0, 1),
        QuizQuestion("What shape is a standard football pitch center circle?", listOf("Circle", "Square", "Rectangle", "Oval"), 0, 1),
        QuizQuestion("Which club is known as the 'Red Devils'?", listOf("Manchester United", "Liverpool", "Arsenal", "Chelsea"), 0, 1),
        QuizQuestion("How many World Cups has Brazil won?", listOf("5", "3", "4", "6"), 0, 1),
        QuizQuestion("Which stadium is home to FC Barcelona?", listOf("Camp Nou", "Santiago Bernabéu", "Wembley", "San Siro"), 0, 1),
        QuizQuestion("Who scored the 'Hand of God' goal?", listOf("Diego Maradona", "Pelé", "Lionel Messi", "Gary Lineker"), 0, 1),
        QuizQuestion("What is a hat-trick in football?", listOf("Three goals by one player in a match", "Three consecutive wins", "Three saves by a goalkeeper", "Three yellow cards"), 0, 1),
        // Level 2
        QuizQuestion("Which nation won UEFA Euro 2004 against the host Portugal?", listOf("Greece", "Czech Republic", "Netherlands", "France"), 1, 2),
        QuizQuestion("In which season did Leicester City win the Premier League?", listOf("2015-16", "2014-15", "2016-17", "2013-14"), 0, 2),
        QuizQuestion("Which club has won the most UEFA Champions League titles?", listOf("Real Madrid", "AC Milan", "Bayern Munich", "Liverpool"), 0, 2),
        QuizQuestion("Who scored the winning goal in the 2010 World Cup final?", listOf("Andrés Iniesta", "David Villa", "Wesley Sneijder", "Xavi"), 0, 2),
        QuizQuestion("Which country won the 2021 Copa América?", listOf("Argentina", "Brazil", "Chile", "Uruguay"), 0, 2),
        QuizQuestion("Who managed Manchester United from 1986 to 2013?", listOf("Sir Alex Ferguson", "Matt Busby", "José Mourinho", "Arsène Wenger"), 0, 2),
        QuizQuestion("Which country has won the most UEFA European Championship titles?", listOf("Germany", "Spain", "France", "Italy"), 0, 2),
        QuizQuestion("What is the distance of a standard penalty kick from the goal?", listOf("11 meters", "10 meters", "12 yards", "18 yards"), 0, 2),
        QuizQuestion("Which goalkeeper has the most Premier League clean sheets?", listOf("Petr Čech", "David de Gea", "Edwin van der Sar", "Joe Hart"), 0, 2),
        QuizQuestion("Which African nation reached the 2022 World Cup semifinals?", listOf("Morocco", "Senegal", "Ghana", "Egypt"), 0, 2),
        QuizQuestion("Who won the Golden Boot at the 2022 World Cup?", listOf("Kylian Mbappé", "Lionel Messi", "Cristiano Ronaldo", "Olivier Giroud"), 0, 2),
        QuizQuestion("Which English club won the 2012 UEFA Champions League?", listOf("Chelsea", "Manchester United", "Liverpool", "Manchester City"), 0, 2),
        QuizQuestion("What does VAR stand for?", listOf("Video Assistant Referee", "Virtual Assistant Referee", "Visual Action Review", "Variable Arbitration Rule"), 0, 2),
        QuizQuestion("Which country hosted the 2010 FIFA World Cup?", listOf("South Africa", "Germany", "Brazil", "Japan"), 0, 2),
        QuizQuestion("Who is the all-time top scorer in Premier League history?", listOf("Alan Shearer", "Wayne Rooney", "Harry Kane", "Andy Cole"), 0, 2),
        QuizQuestion("Which Italian club is based in Turin?", listOf("Juventus", "AC Milan", "Inter Milan", "Roma"), 0, 2),
        QuizQuestion("Which team has won the most FA Cup titles?", listOf("Arsenal", "Manchester United", "Chelsea", "Liverpool"), 0, 2),
        QuizQuestion("What year was the first Premier League season played?", listOf("1992-93", "1990-91", "1994-95", "1988-89"), 0, 2),
        QuizQuestion("Which country has never missed a World Cup since 1930?", listOf("Brazil", "Germany", "Italy", "Argentina"), 0, 2),
        QuizQuestion("Which player has been sent off the most times in La Liga history?", listOf("Sergio Ramos", "Gerard Piqué", "Pepe", "Xabi Alonso"), 0, 2),
        // Level 3
        QuizQuestion("Who scored the winning goal in the 2014 FIFA World Cup final?", listOf("Mario Götze", "Thomas Müller", "André Schürrle", "Miroslav Klose"), 0, 3),
        QuizQuestion("Which goalkeeper holds the record for most clean sheets in Premier League history?", listOf("Petr Čech", "David Seaman", "Edwin van der Sar", "David de Gea"), 0, 3),
        QuizQuestion("Which club did Lionel Messi leave Barcelona for in 2021?", listOf("Paris Saint-Germain", "Inter Miami", "Manchester City", "Chelsea"), 0, 3),
        QuizQuestion("Who won the 2019 Ballon d'Or?", listOf("Lionel Messi", "Virgil van Dijk", "Cristiano Ronaldo", "Sadio Mané"), 0, 3),
        QuizQuestion("Which country won the 2019 Copa América?", listOf("Brazil", "Argentina", "Chile", "Peru"), 0, 3),
        QuizQuestion("Who scored the 'Bicycle kick' goal for Manchester United in the 2011 Premier League?", listOf("Wayne Rooney", "Cristiano Ronaldo", "Dimitar Berbatov", "Javier Hernández"), 0, 3),
        QuizQuestion("Which country defeated France in the 2022 World Cup final?", listOf("Argentina", "Morocco", "Croatia", "England"), 0, 3),
        QuizQuestion("Which club won the 2023 UEFA Champions League?", listOf("Manchester City", "Inter Milan", "Real Madrid", "Bayern Munich"), 0, 3),
        QuizQuestion("Who was the top scorer of the 2018 World Cup?", listOf("Harry Kane", "Kylian Mbappé", "Antoine Griezmann", "Romelu Lukaku"), 0, 3),
        QuizQuestion("What is the 'Panenka' in football?", listOf("A chipped penalty kick", "A backheel pass", "A scorpion kick", "A slide tackle"), 0, 3),
        QuizQuestion("Which English club has won the most Premier League titles?", listOf("Manchester United", "Liverpool", "Chelsea", "Arsenal"), 0, 3),
        QuizQuestion("Who scored the famous 'Aguerooooo' goal that won Manchester City the 2012 title?", listOf("Sergio Agüero", "Carlos Tevez", "Mario Balotelli", "David Silva"), 0, 3),
        QuizQuestion("Which stadium hosted the 2022 UEFA Champions League final?", listOf("Stade de France", "Atatürk Olympic Stadium", "Wembley", "Estádio do Dragão"), 0, 3),
        QuizQuestion("Who is the youngest player to score in a World Cup match?", listOf("Pelé", "Kylian Mbappé", "Lionel Messi", "Michael Owen"), 0, 3),
        QuizQuestion("Which club is known as 'The Invincibles' for going undefeated in 2003-04?", listOf("Arsenal", "Manchester United", "Chelsea", "Liverpool"), 0, 3),
        QuizQuestion("Who won the 2022 Ballon d'Or?", listOf("Karim Benzema", "Lionel Messi", "Sadio Mané", "Kevin De Bruyne"), 0, 3),
        QuizQuestion("Which club did Erling Haaland join in 2022?", listOf("Manchester City", "Real Madrid", "Bayern Munich", "Paris Saint-Germain"), 0, 3),
        QuizQuestion("Which country won the 2017 Africa Cup of Nations?", listOf("Cameroon", "Egypt", "Senegal", "Nigeria"), 0, 3),
        QuizQuestion("Who is the only player to win the World Cup in three different decades?", listOf("Pelé", "Miroslav Klose", "Lionel Messi", "Zinedine Zidane"), 0, 3),
        QuizQuestion("Which club did Zlatan Ibrahimović famously score a 30-yard bicycle kick against?", listOf("England", "Italy", "France", "Norway"), 0, 3),
        // Level 4
        QuizQuestion("Against which club did Erling Haaland score five goals in a single Champions League match in 2023?", listOf("RB Leipzig", "Borussia Dortmund", "Celtic", "Young Boys"), 0, 4),
        QuizQuestion("Who managed Inter Milan to the treble in 2009-10?", listOf("José Mourinho", "Carlo Ancelotti", "Roberto Mancini", "Rafael Benítez"), 1, 4),
        QuizQuestion("Which player has the most assists in Premier League history?", listOf("Ryan Giggs", "Kevin De Bruyne", "Cesc Fàbregas", "Frank Lampard"), 0, 4),
        QuizQuestion("Which country hosted the 2019 Women's World Cup?", listOf("France", "Canada", "Germany", "Australia"), 0, 4),
        QuizQuestion("Which club won the 2020-21 Serie A title, ending Juventus's streak?", listOf("Inter Milan", "AC Milan", "Napoli", "Atalanta"), 0, 4),
        QuizQuestion("Who scored a hat-trick in the 2022 World Cup final?", listOf("Kylian Mbappé", "Lionel Messi", "Ángel Di María", "Julian Álvarez"), 0, 4),
        QuizQuestion("Which goalkeeper scored a last-minute header to save his team in the Premier League?", listOf("Alisson Becker", "Ederson", "Hugo Lloris", "David de Gea"), 0, 4),
        QuizQuestion("Which club finished second in the 2022-23 Premier League?", listOf("Arsenal", "Manchester City", "Newcastle United", "Liverpool"), 0, 4),
        QuizQuestion("Which country won the 2019 UEFA Nations League?", listOf("Portugal", "Netherlands", "France", "Spain"), 0, 4),
        QuizQuestion("Who scored the goal that won Germany the 2014 World Cup?", listOf("Mario Götze", "Miroslav Klose", "Thomas Müller", "Toni Kroos"), 0, 4),
        QuizQuestion("Which player has the most international goals of all time?", listOf("Cristiano Ronaldo", "Lionel Messi", "Ali Mabkhout", "Sunil Chhetri"), 0, 4),
        QuizQuestion("Which club has won back-to-back UEFA Women's Champions League titles?", listOf("Lyon", "Barcelona", "Wolfsburg", "Chelsea"), 0, 4),
        QuizQuestion("Who was the top scorer of the 2014 World Cup?", listOf("James Rodríguez", "Thomas Müller", "Lionel Messi", "Neymar"), 0, 4),
        QuizQuestion("Which English team reached the 2023 FA Cup final?", listOf("Manchester City", "Manchester United", "Arsenal", "Brighton"), 0, 4),
        QuizQuestion("Which player is known as 'Il Fenomeno' (The Phenomenon)?", listOf("Ronaldo Nazário", "Pelé", "Romário", "Ronaldinho"), 0, 4),
        QuizQuestion("Which nation won the 2022 AFC Asian Cup?", listOf("Qatar", "Japan", "South Korea", "Saudi Arabia"), 0, 4),
        QuizQuestion("Which player scored a goal directly from a corner kick in the Premier League in 2023?", listOf("Trent Alexander-Arnold", "Kevin De Bruyne", "Bukayo Saka", "James Ward-Prowse"), 0, 4),
        QuizQuestion("Which club defeated Bayern Munich in the 2023 DFL-Supercup?", listOf("RB Leipzig", "Borussia Dortmund", "Eintracht Frankfurt", "Bayer Leverkusen"), 0, 4),
        QuizQuestion("Who was the captain of Argentina's 2022 World Cup winning team?", listOf("Lionel Messi", "Ángel Di María", "Emiliano Martínez", "Nicolás Otamendi"), 0, 4),
        QuizQuestion("Which club won the 2018-19 UEFA Europa League?", listOf("Chelsea", "Arsenal", "Valencia", "Eintracht Frankfurt"), 0, 4),
        // Level 5
        QuizQuestion("Which player won the Ballon d'Or in 2006 without winning the World Cup that year?", listOf("Fabio Cannavaro", "Ronaldinho", "Zinedine Zidane", "Thierry Henry"), 0, 5),
        QuizQuestion("What was the UEFA Champions League called before 1992?", listOf("European Cup", "Cup Winners' Cup", "Intertoto Cup", "Super Cup"), 0, 5),
        QuizQuestion("Who scored the 'Goal of the Century' for Argentina against England in 1986?", listOf("Diego Maradona", "Jorge Valdano", "Claudio Caniggia", "Gabriel Batistuta"), 0, 5),
        QuizQuestion("Which club did Ronaldo Nazário play for when he won his second Ballon d'Or in 2002?", listOf("Real Madrid", "Inter Milan", "Barcelona", "AC Milan"), 0, 5),
        QuizQuestion("Which player scored 91 goals in a single calendar year (2012)?", listOf("Lionel Messi", "Cristiano Ronaldo", "Robert Lewandowski", "Zlatan Ibrahimović"), 0, 5),
        QuizQuestion("Which club went undefeated in the 2003-04 Premier League season?", listOf("Arsenal", "Chelsea", "Manchester United", "Liverpool"), 0, 5),
        QuizQuestion("Who won the Golden Ball at the 2010 World Cup?", listOf("Diego Forlán", "Wesley Sneijder", "David Villa", "Andrés Iniesta"), 0, 5),
        QuizQuestion("Which player has scored the most goals in a single Premier League season (38 games)?", listOf("Erling Haaland", "Alan Shearer", "Mohamed Salah", "Cristiano Ronaldo"), 0, 5),
        QuizQuestion("Which country won the 2013 Africa Cup of Nations?", listOf("Nigeria", "Egypt", "Cameroon", "Ghana"), 0, 5),
        QuizQuestion("Who scored the 'Scorpion Kick' goal for Colombia against England in 1995?", listOf("René Higuita", "Carlos Valderrama", "Faustino Asprilla", "Iván Zamorano"), 0, 5),
        QuizQuestion("Which Dutch legend invented 'Total Football' as a philosophy?", listOf("Rinus Michels", "Johan Cruyff", "Frank Rijkaard", "Marco van Basten"), 0, 5),
        QuizQuestion("Which club bought Paul Pogba from Manchester United in 2012 on a free transfer?", listOf("Juventus", "Real Madrid", "Barcelona", "Paris Saint-Germain"), 0, 5),
        QuizQuestion("Which country lost all three group matches at the 2018 World Cup as the host?", listOf("Russia", "Qatar", "Brazil", "Germany"), 1, 5),
        QuizQuestion("Which player scored 5 goals in a single Champions League match in 2023?", listOf("Erling Haaland", "Kylian Mbappé", "Vinícius Júnior", "Mohamed Salah"), 0, 5),
        QuizQuestion("Which club won the 1999 UEFA Champions League final in dramatic fashion?", listOf("Manchester United", "Bayern Munich", "Juventus", "Real Madrid"), 0, 5),
        QuizQuestion("Who is the only player to have won the World Cup, European Championship, and Champions League as both player and manager?", listOf("Franz Beckenbauer", "Didier Deschamps", "Zinedine Zidane", "Pep Guardiola"), 0, 5),
        QuizQuestion("Which country reached the 1998 World Cup final as hosts and won?", listOf("France", "Germany", "Italy", "Brazil"), 0, 5),
        QuizQuestion("Which African nation reached the quarterfinals of the 2010 World Cup?", listOf("Ghana", "Nigeria", "Cameroon", "Senegal"), 0, 5),
        QuizQuestion("Who is the top scorer in UEFA Champions League history?", listOf("Cristiano Ronaldo", "Lionel Messi", "Robert Lewandowski", "Karim Benzema"), 0, 5),
        QuizQuestion("Which club won the 2013-14 UEFA Europa League?", listOf("Sevilla", "Benfica", "Juventus", "Atlétiico Madrid"), 0, 5),
        // Level 6
        QuizQuestion("Which player scored in three separate Champions League finals for the same club?", listOf("Cristiano Ronaldo", "Raúl", "Karim Benzema", "Lionel Messi"), 0, 6),
        QuizQuestion("Which club did Zinedine Zidane join before his 2001 transfer to Real Madrid?", listOf("Juventus", "Bordeaux", "Cannes", "Marseille"), 0, 6),
        QuizQuestion("Who is the only player to have scored in four different World Cups for England?", listOf("Gary Lineker", "Wayne Rooney", "Michael Owen", "Harry Kane"), 0, 6),
        QuizQuestion("Which club did Lionel Messi make his senior debut against in 2004?", listOf("Espanyol", "Real Madrid", "Valencia", "Porto"), 0, 6),
        QuizQuestion("Which Italian club did Sweden's Zlatan Ibrahimović never play for?", listOf("AC Milan", "Juventus", "Inter Milan", "Roma"), 3, 6),
        QuizQuestion("Which player has the most assists in UEFA Champions League history?", listOf("Cristiano Ronaldo", "Lionel Messi", "Ryan Giggs", "Angel Di María"), 0, 6),
        QuizQuestion("Who managed Greece to their shock UEFA Euro 2004 victory?", listOf("Otto Rehhagel", "Fernando Santos", "Michael Skibbe", "Angelos Anastasiadis"), 0, 6),
        QuizQuestion("Which club holds the record for the longest unbeaten run in La Liga history?", listOf("Barcelona", "Real Madrid", "Atlético Madrid", "Valencia"), 0, 6),
        QuizQuestion("Which Belgian player won the 2018 World Cup Golden Glove?", listOf("Thibaut Courtois", "Kasper Schmeichel", "Hugo Lloris", "Daniel Subasic"), 0, 6),
        QuizQuestion("Which club did Manuel Neuer join before the 2011-12 season?", listOf("Bayern Munich", "Schalke 04", "Borussia Dortmund", "VfB Stuttgart"), 0, 6),
        QuizQuestion("Who scored the first hat-trick in a World Cup final since 1966?", listOf("Kylian Mbappé", "Lionel Messi", "Karim Benzema", "Thomas Müller"), 0, 6),
        QuizQuestion("Which country won the 1992 UEFA European Championship as a late replacement?", listOf("Denmark", "Sweden", "Netherlands", "Germany"), 0, 6),
        QuizQuestion("Which legendary Dutch forward won the Ballon d'Or in 1987?", listOf("Ruud Gullit", "Marco van Basten", "Johan Cruyff", "Frank Rijkaard"), 0, 6),
        QuizQuestion("Which English manager led the national team to the 2018 World Cup semifinals?", listOf("Gareth Southgate", "Roy Hodgson", "Fabio Capello", "Sven-Göran Eriksson"), 0, 6),
        QuizQuestion("Which club won the 2020-21 Bundesliga title?", listOf("Bayern Munich", "Borussia Dortmund", "RB Leipzig", "VfL Wolfsburg"), 0, 6),
        QuizQuestion("Which player scored the 'Rabona' goal for Tottenham against Arsenal in 2008?", listOf("Robbie Keane", "Dimitar Berbatov", "Jermain Defoe", "Aaron Lennon"), 0, 6),
        QuizQuestion("Who is the only goalkeeper to have won the Ballon d'Or since 1963?", listOf("Lev Yashin", "Gianluigi Buffon", "Manuel Neuer", "Oliver Kahn"), 0, 6),
        QuizQuestion("Which country won the 2007 AFC Asian Cup?", listOf("Iraq", "Japan", "South Korea", "Saudi Arabia"), 0, 6),
        QuizQuestion("Which club did Luis Suárez bite an opponent for the third time against?", listOf("Chelsea", "Juventus", "PSG", "Roma"), 0, 6),
        QuizQuestion("Which manager led Liverpool to Champions League glory in 2005 and 2019?", listOf("Rafael Benítez", "Jürgen Klopp", "Brendan Rodgers", "Gérard Houllier"), 0, 6),
        // Level 7
        QuizQuestion("In the 2005 Champions League final, which Liverpool player scored twice in the comeback against AC Milan?", listOf("Steven Gerrard", "Vladimir Šmicer", "Xabi Alonso", "Djibril Cissé"), 0, 7),
        QuizQuestion("Who was the top scorer at the 1986 FIFA World Cup in Mexico?", listOf("Gary Lineker", "Diego Maradona", "Emilio Butragueño", "Igor Belanov"), 0, 7),
        QuizQuestion("Which player holds the record for most goals in a single Premier League season (42 games)?", listOf("Alan Shearer", "Andy Cole", "Mohamed Salah", "Erling Haaland"), 0, 7),
        QuizQuestion("Which club did Pep Guardiola manage before joining Manchester City?", listOf("Bayern Munich", "Barcelona B", "Juventus", "PSG"), 0, 7),
        QuizQuestion("Who scored the only goal in the 2016 Champions League final between Real Madrid and Atlético Madrid?", listOf("Sergio Ramos", "Gareth Bale", "Cristiano Ronaldo", "Karim Benzema"), 0, 7),
        QuizQuestion("Which country defeated Sweden 3-0 in the 1958 World Cup final on home soil?", listOf("Brazil", "Germany", "France", "Italy"), 0, 7),
        QuizQuestion("Which player scored a hat-trick within 3 minutes for Liverpool against Arsenal in 2018?", listOf("Mohamed Salah", "Roberto Firmino", "Sadio Mané", "Philippe Coutinho"), 0, 7),
        QuizQuestion("Which club did Brazilian legend Pelé play his entire career for?", listOf("Santos", "Flamengo", "São Paulo", "Cruzeiro"), 0, 7),
        QuizQuestion("Who is the oldest goalscorer in UEFA Champions League history?", listOf("Francesco Totti", "Zlatan Ibrahimović", "Ryan Giggs", "Paolo Maldini"), 0, 7),
        QuizQuestion("Which club won the 2011-12 Serie A title undefeated?", listOf("Juventus", "AC Milan", "Inter Milan", "Napoli"), 0, 7),
        QuizQuestion("Which goalkeeper saved three penalties in the 2022 World Cup final shootout?", listOf("Emiliano Martínez", "Hugo Lloris", "Alisson Becker", "Yassine Bounou"), 0, 7),
        QuizQuestion("Which player scored the goal that took Arsenal to the 2006 Champions League final?", listOf("Thierry Henry", "Jens Lehmann", "Robert Pirès", "Fredrik Ljungberg"), 0, 7),
        QuizQuestion("Who won the 2012 Ballon d'Or?", listOf("Lionel Messi", "Cristiano Ronaldo", "Andrés Iniesta", "Iker Casillas"), 0, 7),
        QuizQuestion("Which club did Diego Maradona play for when he won the 1987 Serie A title?", listOf("Napoli", "Juventus", "AC Milan", "Roma"), 0, 7),
        QuizQuestion("Who scored the fastest goal in World Cup history (11 seconds)?", listOf("Hakan Şükür", "Robbie Keane", "Clint Dempsey", "Vaclav Masek"), 0, 7),
        QuizQuestion("Which German club won the 1997 UEFA Champions League?", listOf("Borussia Dortmund", "Bayern Munich", "Werder Bremen", "Bayer Leverkusen"), 0, 7),
        QuizQuestion("Which player has scored the most goals in FIFA Club World Cup history?", listOf("Cristiano Ronaldo", "Lionel Messi", "Karim Benzema", "Gareth Bale"), 0, 7),
        QuizQuestion("Which French club did Zinedine Zidane start his professional career with?", listOf("Cannes", "Bordeaux", "Marseille", "Monaco"), 0, 7),
        QuizQuestion("Which country won the 1978 World Cup on home soil?", listOf("Argentina", "Netherlands", "Italy", "Brazil"), 0, 7),
        QuizQuestion("Which Romanian striker won the Ballon d'Or in 2000?", listOf("Gheorghe Hagi", "Adrian Mutu", "Florin Răducioiu", "Dan Petrescu"), 0, 7),
        // Level 8
        QuizQuestion("Which nation eliminated Spain on penalties in the 2022 World Cup round of 16?", listOf("Morocco", "Croatia", "France", "Brazil"), 0, 8),
        QuizQuestion("Who scored an 'Olympic goal' from a direct corner for Aston Villa in 2020?", listOf("Anwar El Ghazi", "Jack Grealish", "Tyrone Mings", "Douglas Luiz"), 0, 8),
        QuizQuestion("Which club holds the record for most consecutive La Liga titles?", listOf("Real Madrid", "Barcelona", "Atlético Madrid", "Athletic Bilbao"), 0, 8),
        QuizQuestion("Who scored the goal that sent Germany out of the 2018 World Cup group stage?", listOf("Son Heung-min", "Kim Young-gwon", "Toni Kroos", "Mats Hummels"), 0, 8),
        QuizQuestion("Which player won the Premier League Golden Boot in two consecutive seasons for two different clubs?", listOf("Harry Kane", "Mohamed Salah", "Luis Suárez", "Robin van Persie"), 0, 8),
        QuizQuestion("Which club did Alfredo Di Stéfano play for when Real Madrid won five European Cups?", listOf("Real Madrid", "Barcelona", "Valencia", "Espanyol"), 0, 8),
        QuizQuestion("Who scored the winning penalty in the 2012 Champions League final shootout?", listOf("Didier Drogba", "Frank Lampard", "Juan Mata", "David Luiz"), 0, 8),
        QuizQuestion("Which German player scored the most goals in a single World Cup tournament (5 in 2010)?", listOf("Thomas Müller", "Miroslav Klose", "Lukas Podolski", "Mesut Özil"), 0, 8),
        QuizQuestion("Which club won the 2001 Copa Libertadores?", listOf("Boca Juniors", "River Plate", "São Paulo", "Peñarol"), 0, 8),
        QuizQuestion("Which player scored a 40-yard goal for Manchester United against Aston Villa in 2010?", listOf("Wayne Rooney", "Cristiano Ronaldo", "Paul Scholes", "Ryan Giggs"), 0, 8),
        QuizQuestion("Which country won the 2000 UEFA European Championship via a golden goal?", listOf("France", "Italy", "Germany", "Netherlands"), 0, 8),
        QuizQuestion("Which club did Kaká play for when he won the Ballon d'Or in 2007?", listOf("AC Milan", "Real Madrid", "São Paulo", "Chelsea"), 0, 8),
        QuizQuestion("Which club was the first to retain the UEFA Champions League under its new format?", listOf("AC Milan", "Real Madrid", "Barcelona", "Manchester United"), 0, 8),
        QuizQuestion("Who scored the goal that denied England a spot in the 1974 World Cup?", listOf("Poland", "Yugoslavia", "Czechoslovakia", "Wales"), 0, 8),
        QuizQuestion("Which player scored 5 goals in a single Bundesliga match in 2013?", listOf("Robert Lewandowski", "Mario Gómez", "Claudio Pizarro", "Pierre-Emerick Aubameyang"), 0, 8),
        QuizQuestion("Which Dutch winger was known for his 'Cruyff Turn' move?", listOf("Johan Cruyff", "Marco van Basten", "Ruud Gullit", "Arjen Robben"), 0, 8),
        QuizQuestion("Which country won the 2010 AFC Asian Cup?", listOf("Japan", "Australia", "South Korea", "Iraq"), 0, 8),
        QuizQuestion("Which Chelsea manager won the Premier League in his first season (2016-17)?", listOf("Antonio Conte", "José Mourinho", "Guus Hiddink", "Carlo Ancelotti"), 0, 8),
        QuizQuestion("Which striker scored a perfect hat-trick (left foot, right foot, header) in a World Cup final?", listOf("Geoff Hurst", "Kylian Mbappé", "Gerd Müller", "Zinedine Zidane"), 1, 8),
        QuizQuestion("Which club won the 2015-16 UEFA Champions League?", listOf("Real Madrid", "Atlético Madrid", "Bayern Munich", "Manchester City"), 0, 8),
        // Level 9
        QuizQuestion("Which club did Johan Cruyff manage before his legendary Barcelona 'Dream Team' era?", listOf("Ajax", "Feyenoord", "AZ Alkmaar", "Sparta Rotterdam"), 0, 9),
        QuizQuestion("Who is the youngest goalscorer in Champions League knockout-stage history?", listOf("Bojan Krkić", "Ansu Fati", "Pedri", "Jamal Musiala"), 0, 9),
        QuizQuestion("Which player scored a goal from the halfway line against Bayern Munich in the 2014 Super Cup?", listOf("Alvaro Morata", "Gareth Bale", "Cristiano Ronaldo", "Karim Benzema"), 0, 9),
        QuizQuestion("Which club did George Best famously score six goals for in a single FA Cup match?", listOf("Manchester United", "Fulham", "Stockport County", "Bournemouth"), 0, 9),
        QuizQuestion("Which Austrian striker won the Ballon d'Or in 1964?", listOf("Josef Masopust", "Denis Law", "Eusébio", "Bobby Charlton"), 2, 9),
        QuizQuestion("Which club stopped Celtic's 25-match winning run in 2004?", listOf("Rangers", "Aberdeen", "Hearts", "Dundee United"), 0, 9),
        QuizQuestion("Which player has the most assists in a single Premier League season (20)?", listOf("Thierry Henry", "Kevin De Bruyne", "Cesc Fàbregas", "Mesut Özil"), 0, 9),
        QuizQuestion("Which non-league club famously defeated Premier League Burnley in the 2014-15 FA Cup?", listOf("Lincoln City", "Blyth Spartans", "Sutton United", "Aldershot Town"), 0, 9),
        QuizQuestion("Which player scored the 'Goal of the Tournament' at the 2018 World Cup for France against Argentina?", listOf("Kylian Mbappé", "Antoine Griezmann", "Benjamin Pavard", "Paul Pogba"), 2, 9),
        QuizQuestion("Which club did Socrates play for when he won three Brazilian league titles?", listOf("Corinthians", "Flamengo", "Santos", "São Paulo"), 0, 9),
        QuizQuestion("Which Spanish referee awarded three penalties in the 2018 World Cup final?", listOf("Néstor Pitana", "Felix Brych", "Björn Kuipers", "Cüneyt Çakır"), 0, 9),
        QuizQuestion("Which player won the Golden Ball at the 1982 World Cup?", listOf("Paolo Rossi", "Dino Zoff", "Zico", "Karl-Heinz Rummenigge"), 0, 9),
        QuizQuestion("Which Swedish club did Zlatan Ibrahimović make his professional debut for?", listOf("Malmö FF", "AIK", "IFK Göteborg", "Hammarby"), 0, 9),
        QuizQuestion("Which country eliminated Portugal in the group stage of the 2014 World Cup?", listOf("Germany", "USA", "Ghana", "Algeria"), 0, 9),
        QuizQuestion("Which player scored the winning goal for Juventus in the 1996 Champions League final?", listOf("Fabrizio Ravanelli", "Alessandro Del Piero", "Gianluca Vialli", "Zinedine Zidane"), 0, 9),
        QuizQuestion("Which manager led Bayer Leverkusen to an unbeaten Bundesliga season in 2023-24?", listOf("Xabi Alonso", "Gerardo Seoane", "Peter Bosz", "Hannes Wolf"), 0, 9),
        QuizQuestion("Which club won the 1954 World Cup in a surprise victory over Hungary?", listOf("West Germany", "Austria", "Switzerland", "Uruguay"), 0, 9),
        QuizQuestion("Which player wore the iconic number 10 shirt for Brazil in the 1970 World Cup?", listOf("Pelé", "Rivelino", "Jairzinho", "Tostão"), 0, 9),
        QuizQuestion("Which African club won the 2022 CAF Champions League?", listOf("Wydad Casablanca", "Al Ahly", "Espérance", "Mamelodi Sundowns"), 0, 9),
        QuizQuestion("Which Hungarian legend scored 84 goals in 85 international matches?", listOf("Ferenc Puskás", "Sándor Kocsis", "Flórián Albert", "László Kubala"), 0, 9),
        // Level 10
        QuizQuestion("In which year did the offside rule change to require only one attacker level with the second-last defender?", listOf("1925", "1990", "2005", "2018"), 0, 10),
        QuizQuestion("Which player scored the fastest hat-trick in Premier League history (2 min 56 sec)?", listOf("Sadio Mané", "Robbie Fowler", "Alan Shearer", "Erling Haaland"), 0, 10),
        QuizQuestion("Which club holds the record for most consecutive wins in the UEFA Champions League?", listOf("Bayern Munich", "Real Madrid", "Barcelona", "AC Milan"), 0, 10),
        QuizQuestion("Which player scored the most goals in a single World Cup tournament (13 in 1958)?", listOf("Just Fontaine", "Pelé", "Gerd Müller", "Ronaldo Nazário"), 0, 10),
        QuizQuestion("Which Argentine manager won the World Cup as both a player and a coach (not the same year)?", listOf("César Luis Menotti", "Alfio Basile", "Daniel Passarella", "Carlos Bilardo"), 0, 10),
        QuizQuestion("Which Ivorian player scored the winning penalty in the 2019 AFCON final?", listOf("Wilfried Zaha", "Nicolas Pépé", "Franck Kessié", "Serey Dié"), 2, 10),
        QuizQuestion("Which goalkeeper has saved the most penalties in Premier League history?", listOf("David James", "Petr Čech", "Mark Schwarzer", "Brad Friedel"), 0, 10),
        QuizQuestion("Which club won the first FIFA Club World Cup in 2000?", listOf("Corinthians", "Real Madrid", "Manchester United", "Boca Juniors"), 0, 10),
        QuizQuestion("Which player scored a bicycle kick for Juventus against Real Madrid in the 2018 Champions League quarterfinal?", listOf("Cristiano Ronaldo", "Gonzalo Higuaín", "Paulo Dybala", "Mario Mandžukić"), 0, 10),
        QuizQuestion("Which stadium was the first all-seater stadium in England?", listOf("Hillsborough", "Old Trafford", "Highbury", "Anfield"), 0, 10),
        QuizQuestion("Which country won the 1960 European Nations' Cup (the first edition)?", listOf("Soviet Union", "Yugoslavia", "Czechoslovakia", "France"), 0, 10),
        QuizQuestion("Which player scored the most goals in a single edition of the Copa América (9 in 1957)?", listOf("Humberto Maschio", "Pelé", "Gabriel Batistuta", "Severino Varela"), 0, 10),
        QuizQuestion("Which English club was the first to win the European Cup (now Champions League)?", listOf("Manchester United", "Liverpool", "Nottingham Forest", "Aston Villa"), 1, 10),
        QuizQuestion("Which manager won La Liga with two different clubs in consecutive seasons (2013 and 2014)?", listOf("Diego Simeone", "Carlo Ancelotti", "Pep Guardiola", "Unai Emery"), 0, 10),
        QuizQuestion("Which club broke the world transfer record to sign Paul Pogba in 2016?", listOf("Manchester United", "Real Madrid", "Barcelona", "PSG"), 0, 10),
        QuizQuestion("Which player has scored the most goals in a single Copa del Rey season (13 in 2016-17)?", listOf("Lionel Messi", "Cristiano Ronaldo", "Karim Benzema", "Luis Suárez"), 0, 10),
        QuizQuestion("Which referee sent off Zinedine Zidane in the 2006 World Cup final?", listOf("Horacio Elizondo", "Lubos Michel", "Markus Merk", "Pierluigi Collina"), 0, 10),
        QuizQuestion("Which player won the Premier League Golden Boot in three consecutive seasons (1993-1996)?", listOf("Alan Shearer", "Andy Cole", "Robbie Fowler", "Les Ferdinand"), 0, 10),
        QuizQuestion("Which club won the 1948 Copa América (South American Championship)?", listOf("Brazil", "Argentina", "Uruguay", "Paraguay"), 0, 10),
        QuizQuestion("Which player scored a scorpion kick goal for Colombia in 1995?", listOf("René Higuita", "Carlos Valderrama", "Faustino Asprilla", "Adolfo Valencia"), 0, 10)
    )

    fun bundleForDate(date: String, usedQuestions: Set<String> = emptySet()): FallbackBundle {
        val seed = date.hashCode().toLong() * 31 + date.take(4).hashCode().toLong() * 17
        val quote = quotePool[(seed * 7).mod(quotePool.size.coerceAtLeast(1)).toInt()]
        val fact = factPool[(seed * 13).mod(factPool.size.coerceAtLeast(1)).toInt()]
        val quizzes = (1..HomeContentConfig.DAILY_QUIZ_COUNT).map { level ->
            val pool = tieredQuizPool.filter { it.difficulty == level }
            pickFreshQuestion(pool, seed + level * 100, usedQuestions)
        }
        return FallbackBundle(quizzes, quote, fact)
    }

    private fun pickFreshQuestion(pool: List<QuizQuestion>, seed: Long, usedQuestions: Set<String>): QuizQuestion {
        if (pool.isEmpty()) error("No questions for level")
        val unused = pool.filter { it.question !in usedQuestions }
        val target = if (unused.isNotEmpty()) unused else pool
        return target[seed.mod(target.size).toInt()]
    }

    fun padQuizzesToDailySet(existing: List<QuizQuestion>, date: String): List<QuizQuestion> {
        if (existing.size >= HomeContentConfig.DAILY_QUIZ_COUNT) {
            return existing.take(HomeContentConfig.DAILY_QUIZ_COUNT)
                .sortedBy { it.difficulty }
        }
        val fallback = bundleForDate(date).quizzes
        val merged = (existing + fallback)
            .distinctBy { it.question }
            .sortedBy { it.difficulty }
        return if (merged.size >= HomeContentConfig.DAILY_QUIZ_COUNT) {
            merged.take(HomeContentConfig.DAILY_QUIZ_COUNT)
        } else {
            fallback
        }
    }

    data class FallbackBundle(
        val quizzes: List<QuizQuestion>,
        val quote: FootballQuote,
        val fact: String
    )
}
