;
; Potenciometro.asm
;
; Created: 12/11/2019 11:23:55 a. m.
; Author : HikingC7
;


; Replace with your application code

.include<m328pdef.inc>

.def unidad = r20
.def decena = r21
.def centena = r22

.org 0x000

rjmp start

start:
	
	ldi unidad, 0
	ldi decena, 0
	ldi centena, 0

	ldi r16, 0b00100000
	out DDRB, r16

	ldi r16, 0b00000000
	out DDRC, r16

	nop

	call UART_CONFIG
	call ADC_CONFIG

CICLO:

	ldi unidad, 0
	ldi decena, 0
	ldi centena, 0

	call ADC_READ

	mov r16, r18

	call ASCII

	ldi r17, 48
	add unidad, r17
	add decena, r17
	add centena, r17

	mov r16, centena
	call UART_TRANSMIT

	mov r16, decena
	call UART_TRANSMIT

	mov r16, unidad
	call UART_TRANSMIT

	ldi r16, 10
	call UART_TRANSMIT

	ldi r16, 0b00100000
	out PORTB, r16
	call delay_500ms
	ldi r16, 0b00000000
	out PORTB,r16
	call delay_500ms

	rjmp CICLO

ASCII:
L_CENTENA:
	cpi r16, 100
	BRLO L_DECENA
	inc centena
	subi r16, 100
	rjmp L_CENTENA

L_DECENA:
	cpi r16, 10
	BRLO L_UNIDAD
	inc decena
	subi r16, 10
	rjmp L_DECENA

L_UNIDAD:
	cpi r16, 1
	BRLO L_FIN
	inc unidad
	subi r16, 1
	rjmp L_UNIDAD

L_FIN:
	ret

ADC_CONFIG:
	ldi r16, 0b01100000
	sts ADMUX, r16	
	ldi r16, 0b10000000
	sts ADCSRA, r16	
	call delay_10ms	
	ret

ADC_READ:
	ldi r16, 0b11000000
	sts ADCSRA, r16
	call delay_10ms
	ret

CICLO_ADC:
	lds r16, ADCSRA
	sbrc r16, ADSC
	rjmp CICLO_ADC
	lds r17, ADCL
	lds r18, ADCH
	ret

UART_CONFIG:
	ldi r16, 0b00000110
	sts UCSR0C, r16

	ldi r17, 0b00000000
	ldi r18, 0b01100111

	sts UBRR0H, r17 
	sts UBRR0L, r16 

	ldi r16, 0b00011000
	sts UCSR0B, r16
	ret

UART_TRANSMIT:

	LDS r17, UCSR0A

	bst r17, 5
	brts UART_FREE
	rjmp UART_TRANSMIT

UART_FREE:
	sts UDR0, r16
	ret

;Rutina
delay_10ms:
	
	ldi r17, 99

repetir1:
	
	ldi r18, 199

repetir:
	nop
	nop	
	nop
	nop
	nop
	dec r18
	brne repetir
	dec r17
	brne repetir1
	ret

delay_100ms:
	call delay_10ms
	call delay_10ms
	call delay_10ms
	call delay_10ms
	call delay_10ms
	call delay_10ms
	call delay_10ms
	call delay_10ms
	call delay_10ms
	call delay_10ms
	ret


delay_500ms:
	call delay_100ms
	call delay_100ms
	call delay_100ms
	call delay_100ms
	call delay_100ms
	ret