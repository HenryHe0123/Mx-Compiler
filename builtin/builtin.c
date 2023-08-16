#define bool _Bool

void _func_print(char *s) { printf("%s", s); }

void _func_println(char *s) { printf("%s\n", s); }

void _func_printInt(int n) { printf("%d", n); }

void _func_printlnInt(int n) { printf("%d\n", n); }

char *_func_getString() {
    char *s = malloc(1 << 8);
    scanf("%s", s);
    return s;
}

int _func_getInt() {
    int n;
    scanf("%d", &n);
    return n;
}

char *_func_toString(int n) {
    char *s = malloc(1 << 4);
    sprintf(s, "%d", n);
    return s;
}

int __string_length(char *s){
    return strlen(s);
}

char *__string_substring(char *s, int l, int r) {
    char *tmp = malloc(r - l + 1);
    for (int i = l; i < r; i++) tmp[i - l] = s[i];
    tmp[r - l] = '\0';
    return tmp;
}

int __string_parseInt(char *s) {
    int x;
    sscanf(s, "%d", &x);
    return x;
}

int __string_ord(char *s, int n) {
    return s[n];
}

char *__malloc(int size) {
    return (char *) malloc(size);
}

char *__string_add(char *s, char *t) {
    char *p = malloc(strlen(s) + strlen(t) + 1);
    strcpy(p, s);
    strcat(p, t);
    return p;
}

bool __string_lt(char *s, char *t) { return strcmp(s, t) < 0; }

bool __string_le(char *s, char *t) { return strcmp(s, t) <= 0; }

bool __string_gt(char *s, char *t) { return strcmp(s, t) > 0; }

bool __string_ge(char *s, char *t) { return strcmp(s, t) >= 0; }

bool __string_eq(char *s, char *t) { return strcmp(s, t) == 0; }

bool __string_ne(char *s, char *t) { return strcmp(s, t) != 0; }