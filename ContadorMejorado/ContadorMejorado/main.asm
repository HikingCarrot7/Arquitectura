;
; USART Sumador.asm
;
; Created: 24/10/2019 18:19:03 p. m.
; Author : emman
;

.include<m328pdef.inc>
.org 0x00

rjmp start

start:
	ldi r18, 0b00110000
	ldi r20, 10				; registro bandera para reiniciar el ciclo.
	out	DDRD, r18			;Configura el prt D como entrada.
	out DDRB, r16

	nop 

	call UART_CONFIG

CICLO:
	in r21, PIND 			;Lee entrada del puerto D.
	sbis PIND, 7 			;Si el bit de la posicion 0 es 1 se salta un registro, sino continua normal.
	rjmp BOTON_ON
	rjmp BOTON_OFF
	rjmp CICLO

BOTON_ON:

	brbs 0, CICLO 			; Si el bit C del Rr SERG = 1, regresa a ciclo

	mov r16, r18
	call UART_TRANSMIT
	
	dec r20					; controla las repeticiones 
	cpi r20, 0 				; compara que el r21 sea igual a 0
	breq start 				; si el r21  = 0 brinca a start
	
	inc r18

	sec 					;activar bandera de SERG en el bit C=1
	rjmp CICLO

BOTON_OFF:
	clc						;vuelve 0 el bit C de Rr SERG
	rjmp CICLO

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
	brts UART_FREE
	rjmp UART_TRANSMIT

UART_FREE:
	sts UDR0, r16
	ret