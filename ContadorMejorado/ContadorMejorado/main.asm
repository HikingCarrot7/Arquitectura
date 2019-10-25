;
; USART Sumador.asm
;
; Created: 24/10/2019 18:19:03 p. m.
; Author : emman
;

.include<m328pdef.inc>
.org 0X00

RJMP start
; Replace with your application code
start:
	ldi r18, 0b00000000
    ldi r16, 0b00100001
	out DDRB, r16
	nop 
	call UART_CONFIG
 
CICLO:
	
	mov r16, r18
	call UART_TRANSMIT
	inc r18
	cpi r18, 255
	BREQ start
	RJMP CICLO

UART_CONFIG:
	ldi r16, 0b00000110
	sts UCSR0C, r16

	ldi r17, 0b00000000
	ldi r16, 0b01100111

	sts UBRR0H, r17
	sts UBRR0L, r16

	ldi r16, 0b00011000
	sts UCSR0B, r16
	ret

UART_TRANSMIT:
	LDS r17, UCSR0A
	bst r17, 5
	BRTS UART_FREE
	rjmp UART_TRANSMIT

UART_FREE:
	sts UDR0, r16
	ret