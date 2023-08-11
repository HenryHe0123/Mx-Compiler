# Change the src to the path of your java source files
JAVA_SRC = $(shell find src -name '*.java')
# Change this to the path of your antlr jar
ANTLR_JAR = /usr/share/java/antlr-4.12.0-complete.jar

.PHONY: all
all: Compiler

.PHONY: Compiler
Compiler: $(JAVA_SRC)
	javac17 -d bin $(JAVA_SRC) -cp $(ANTLR_JAR)

.PHONY: clean
clean:
	rm -f bin/*.class bin/*.jar