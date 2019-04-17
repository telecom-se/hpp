section .text

; rdi = x, rsi = y
global _sse proc
    movdqa xmm0, [rdi]
    mulps  xmm0, [rsi]
    addps  xmm0, [rsi]
    movdqa [rdi], xmm0
    ret
_sse ENDP