# Change the src to the path of your java source files
JAVA_SRC = $(shell find src -name '*.java')
# Change this to the path of your antlr jar
ANTLR_JAR = /usr/share/java/antlr-4.12.0-complete.jar
ANTLR_G4 = $(shell find -name '*.g4')
G4_DIR = $(shell find -name '*.g4' | xargs dirname | uniq)

.PHONY: all
all: Compiler

.PHONY: Compiler
Compiler: $(JAVA_SRC) antlr-parser
	javac17 -d bin $(JAVA_SRC) -cp $(ANTLR_JAR)

# replace the antlr4 command with your own
.PHONY: antlr-parser
antlr-parser: $(ANTLR_G4)
	antlr4 $^ -visitor -listener -package grammar

.PHONY: clean
clean:
	rm -f $(G4_DIR)/*.java $(G4_DIR)/*.tokens $(G4_DIR)/*.interp
	rm -f bin/*.class bin/*.jar
