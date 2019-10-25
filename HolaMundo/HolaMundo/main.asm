;
; USART.asm
;
; Created: 23/10/2019 12:18:38 p. m.
; Author : emman
;
.include<m328pdef.inc>
.org 0X00
RJMP start
; Replace with your application code
start:
    ldi r16, 0b00100001
	out DDRB, r16
	nop 

	call UART_CONFIG
    
CICLO:
	ldi r16, 'H'
	call UART_TRANSMIT

	ldi r16, 'O'
	call UART_TRANSMIT

	ldi r16, 'L'
	call UART_TRANSMIT

	ldi r16, 'A'
	call UART_TRANSMIT

	ldi r16, ' '
	call UART_TRANSMIT

	ldi r16, 'M'
	call UART_TRANSMIT

	ldi r16, 'U'
	call UART_TRANSMIT

	ldi r16, 'N'
	call UART_TRANSMIT

	ldi r16, 'D'
	call UART_TRANSMIT

	ldi r16, 'O'
	call UART_TRANSMIT

	ldi r16, 10
	call UART_TRANSMIT

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
	lds r17, UCSR0A
	bst r17, 5
	BRTS UART_FREE
	rjmp UART_TRANSMIT

UART_FREE:
	sts UDR0, r16
	ret