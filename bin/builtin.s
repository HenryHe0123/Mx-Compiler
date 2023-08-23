	.text
	.attribute	4, 16
	.attribute	5, "rv32i2p0"
	.file	"builtin.c"
	.globl	_func_print                     # -- Begin function _func_print
	.p2align	1
	.type	_func_print,@function
_func_print:                            # @_func_print
# %bb.0:
	lui	a1, %hi(.L.str)
	addi	a1, a1, %lo(.L.str)
	mv	a2, a0
	mv	a0, a1
	mv	a1, a2
	tail	printf
.Lfunc_end0:
	.size	_func_print, .Lfunc_end0-_func_print
                                        # -- End function
	.globl	_func_println                   # -- Begin function _func_println
	.p2align	1
	.type	_func_println,@function
_func_println:                          # @_func_println
# %bb.0:
	lui	a1, %hi(.L.str.1)
	addi	a1, a1, %lo(.L.str.1)
	mv	a2, a0
	mv	a0, a1
	mv	a1, a2
	tail	printf
.Lfunc_end1:
	.size	_func_println, .Lfunc_end1-_func_println
                                        # -- End function
	.globl	_func_printInt                  # -- Begin function _func_printInt
	.p2align	1
	.type	_func_printInt,@function
_func_printInt:                         # @_func_printInt
# %bb.0:
	lui	a1, %hi(.L.str.2)
	addi	a1, a1, %lo(.L.str.2)
	mv	a2, a0
	mv	a0, a1
	mv	a1, a2
	tail	printf
.Lfunc_end2:
	.size	_func_printInt, .Lfunc_end2-_func_printInt
                                        # -- End function
	.globl	_func_printlnInt                # -- Begin function _func_printlnInt
	.p2align	1
	.type	_func_printlnInt,@function
_func_printlnInt:                       # @_func_printlnInt
# %bb.0:
	lui	a1, %hi(.L.str.3)
	addi	a1, a1, %lo(.L.str.3)
	mv	a2, a0
	mv	a0, a1
	mv	a1, a2
	tail	printf
.Lfunc_end3:
	.size	_func_printlnInt, .Lfunc_end3-_func_printlnInt
                                        # -- End function
	.globl	_func_getString                 # -- Begin function _func_getString
	.p2align	1
	.type	_func_getString,@function
_func_getString:                        # @_func_getString
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	sw	s0, 8(sp)                       # 4-byte Folded Spill
	li	a0, 256
	call	malloc
	mv	s0, a0
	lui	a0, %hi(.L.str)
	addi	a0, a0, %lo(.L.str)
	mv	a1, s0
	call	scanf
	mv	a0, s0
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	lw	s0, 8(sp)                       # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end4:
	.size	_func_getString, .Lfunc_end4-_func_getString
                                        # -- End function
	.globl	_func_getInt                    # -- Begin function _func_getInt
	.p2align	1
	.type	_func_getInt,@function
_func_getInt:                           # @_func_getInt
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	lui	a0, %hi(.L.str.2)
	addi	a0, a0, %lo(.L.str.2)
	addi	a1, sp, 8
	call	scanf
	lw	a0, 8(sp)
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end5:
	.size	_func_getInt, .Lfunc_end5-_func_getInt
                                        # -- End function
	.globl	_func_toString                  # -- Begin function _func_toString
	.p2align	1
	.type	_func_toString,@function
_func_toString:                         # @_func_toString
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	sw	s0, 8(sp)                       # 4-byte Folded Spill
	sw	s1, 4(sp)                       # 4-byte Folded Spill
	mv	s0, a0
	li	a0, 16
	call	malloc
	mv	s1, a0
	lui	a0, %hi(.L.str.2)
	addi	a1, a0, %lo(.L.str.2)
	mv	a0, s1
	mv	a2, s0
	call	sprintf
	mv	a0, s1
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	lw	s0, 8(sp)                       # 4-byte Folded Reload
	lw	s1, 4(sp)                       # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end6:
	.size	_func_toString, .Lfunc_end6-_func_toString
                                        # -- End function
	.globl	__string.length                 # -- Begin function __string.length
	.p2align	1
	.type	__string.length,@function
__string.length:                        # @__string.length
# %bb.0:
	tail	strlen
.Lfunc_end7:
	.size	__string.length, .Lfunc_end7-__string.length
                                        # -- End function
	.globl	__string.substring              # -- Begin function __string.substring
	.p2align	1
	.type	__string.substring,@function
__string.substring:                     # @__string.substring
# %bb.0:
	addi	sp, sp, -32
	sw	ra, 28(sp)                      # 4-byte Folded Spill
	sw	s0, 24(sp)                      # 4-byte Folded Spill
	sw	s1, 20(sp)                      # 4-byte Folded Spill
	sw	s2, 16(sp)                      # 4-byte Folded Spill
	sw	s3, 12(sp)                      # 4-byte Folded Spill
	mv	s1, a2
	mv	s0, a1
	mv	s2, a0
	sub	s3, a2, a1
	addi	a0, s3, 1
	call	malloc
	bge	s0, s1, .LBB8_3
# %bb.1:                                # %.preheader
	add	a1, s2, s0
	mv	a2, a0
	mv	a3, s3
.LBB8_2:                                # =>This Inner Loop Header: Depth=1
	lb	a4, 0(a1)
	sb	a4, 0(a2)
	addi	a3, a3, -1
	addi	a2, a2, 1
	addi	a1, a1, 1
	bnez	a3, .LBB8_2
.LBB8_3:
	add	a1, a0, s3
	sb	zero, 0(a1)
	lw	ra, 28(sp)                      # 4-byte Folded Reload
	lw	s0, 24(sp)                      # 4-byte Folded Reload
	lw	s1, 20(sp)                      # 4-byte Folded Reload
	lw	s2, 16(sp)                      # 4-byte Folded Reload
	lw	s3, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 32
	ret
.Lfunc_end8:
	.size	__string.substring, .Lfunc_end8-__string.substring
                                        # -- End function
	.globl	__string.parseInt               # -- Begin function __string.parseInt
	.p2align	1
	.type	__string.parseInt,@function
__string.parseInt:                      # @__string.parseInt
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	lui	a1, %hi(.L.str.2)
	addi	a1, a1, %lo(.L.str.2)
	addi	a2, sp, 8
	call	sscanf
	lw	a0, 8(sp)
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end9:
	.size	__string.parseInt, .Lfunc_end9-__string.parseInt
                                        # -- End function
	.globl	__string.ord                    # -- Begin function __string.ord
	.p2align	1
	.type	__string.ord,@function
__string.ord:                           # @__string.ord
# %bb.0:
	add	a0, a0, a1
	lbu	a0, 0(a0)
	ret
.Lfunc_end10:
	.size	__string.ord, .Lfunc_end10-__string.ord
                                        # -- End function
	.globl	__malloc                        # -- Begin function __malloc
	.p2align	1
	.type	__malloc,@function
__malloc:                               # @__malloc
# %bb.0:
	tail	malloc
.Lfunc_end11:
	.size	__malloc, .Lfunc_end11-__malloc
                                        # -- End function
	.globl	__string.add                    # -- Begin function __string.add
	.p2align	1
	.type	__string.add,@function
__string.add:                           # @__string.add
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	sw	s0, 8(sp)                       # 4-byte Folded Spill
	sw	s1, 4(sp)                       # 4-byte Folded Spill
	sw	s2, 0(sp)                       # 4-byte Folded Spill
	mv	s2, a1
	mv	s1, a0
	call	strlen
	mv	s0, a0
	mv	a0, s2
	call	strlen
	add	a0, a0, s0
	addi	a0, a0, 1
	call	malloc
	mv	s0, a0
	mv	a1, s1
	call	strcpy
	mv	a0, s0
	mv	a1, s2
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	lw	s0, 8(sp)                       # 4-byte Folded Reload
	lw	s1, 4(sp)                       # 4-byte Folded Reload
	lw	s2, 0(sp)                       # 4-byte Folded Reload
	addi	sp, sp, 16
	tail	strcat
.Lfunc_end12:
	.size	__string.add, .Lfunc_end12-__string.add
                                        # -- End function
	.globl	__string.lt                     # -- Begin function __string.lt
	.p2align	1
	.type	__string.lt,@function
__string.lt:                            # @__string.lt
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	call	strcmp
	srli	a0, a0, 31
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end13:
	.size	__string.lt, .Lfunc_end13-__string.lt
                                        # -- End function
	.globl	__string.le                     # -- Begin function __string.le
	.p2align	1
	.type	__string.le,@function
__string.le:                            # @__string.le
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	call	strcmp
	slti	a0, a0, 1
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end14:
	.size	__string.le, .Lfunc_end14-__string.le
                                        # -- End function
	.globl	__string.gt                     # -- Begin function __string.gt
	.p2align	1
	.type	__string.gt,@function
__string.gt:                            # @__string.gt
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	call	strcmp
	sgtz	a0, a0
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end15:
	.size	__string.gt, .Lfunc_end15-__string.gt
                                        # -- End function
	.globl	__string.ge                     # -- Begin function __string.ge
	.p2align	1
	.type	__string.ge,@function
__string.ge:                            # @__string.ge
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	call	strcmp
	not	a0, a0
	srli	a0, a0, 31
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end16:
	.size	__string.ge, .Lfunc_end16-__string.ge
                                        # -- End function
	.globl	__string.eq                     # -- Begin function __string.eq
	.p2align	1
	.type	__string.eq,@function
__string.eq:                            # @__string.eq
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	call	strcmp
	seqz	a0, a0
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end17:
	.size	__string.eq, .Lfunc_end17-__string.eq
                                        # -- End function
	.globl	__string.ne                     # -- Begin function __string.ne
	.p2align	1
	.type	__string.ne,@function
__string.ne:                            # @__string.ne
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	call	strcmp
	snez	a0, a0
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end18:
	.size	__string.ne, .Lfunc_end18-__string.ne
                                        # -- End function
	.type	.L.str,@object                  # @.str
	.section	.rodata.str1.1,"aMS",@progbits,1
.L.str:
	.asciz	"%s"
	.size	.L.str, 3

	.type	.L.str.1,@object                # @.str.1
.L.str.1:
	.asciz	"%s\n"
	.size	.L.str.1, 4

	.type	.L.str.2,@object                # @.str.2
.L.str.2:
	.asciz	"%d"
	.size	.L.str.2, 3

	.type	.L.str.3,@object                # @.str.3
.L.str.3:
	.asciz	"%d\n"
	.size	.L.str.3, 4

	.ident	"clang version 15.0.7"
	.section	".note.GNU-stack","",@progbits
