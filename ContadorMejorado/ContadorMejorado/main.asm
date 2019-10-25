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
	ldi r22, 0b00000000
	ldi r23, 0b11111111
	ldi r18, 48
	ldi r26, 48
	ldi r27, 48
	ldi r20, 255			; registro bandera para reiniciar el ciclo.
	out	DDRD, r22			;Configura el prt D como entrada.
	out DDRB, r23

	nop 

	call UART_CONFIG

CICLO:
	call delay_1000ms
	in r21, PIND 			;Lee entrada del puerto D.
	sbis PIND, 7 			;Si el bit de la posicion 0 es 1 se salta un registro, sino continua normal.
	rjmp BOTON_ON
	rjmp BOTON_OFF
	rjmp CICLO

BOTON_ON:
	brbs 0, CICLO 			; Si el bit C del Rr SERG = 1, regresa a ciclo

	mov r16, r27			;r27 centenas
	call UART_TRANSMIT

	mov r16, r26			;r26 decenas
	call UART_TRANSMIT

	mov r16, r18			;unidades
	call UART_TRANSMIT

	ldi r25, 10
	mov r16, r25
	call UART_TRANSMIT

	dec r20					; controla las repeticiones 
	cpi r20, 0 				; compara que el r21 sea igual a 0
	breq start 	
							; si el r21  = 0 brinca a start

	cpi r18, 58
	call UNIDAD
	;breq DECENA moria por esto no mover

	sec 					;activar bandera de SERG en el bit C=1
	rjmp CICLO

BOTON_OFF:
	clc						;vuelve 0 el bit C de Rr SERG
	rjmp CICLO

UNIDAD:
	inc r18
	cpi r18, 58
	breq DECENA
	ret
	
DECENA:
	inc r26
	cpi r26, 58
	breq CENTENA
	ldi r18, 48
	ret

CENTENA:
	inc r27
	ldi r26, 48
	ldi r18, 48
	ret

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

delay_1000ms:		;Función para retrasar
	ldi r22, 9

repetir2:

	ldi r23, 99

repetir1:

	ldi r24, 199

repetir:

	nop
	nop
	nop
	nop
	nop
	dec r24
	brne repetir ;2 ciclos
	dec r23
	brne repetir1
	dec r22
	brne repetir2
	
	ret