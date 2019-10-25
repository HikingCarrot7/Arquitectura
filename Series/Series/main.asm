


.include <m328pdef.inc>
.org 0x00

;PUERTOD
	ldi r20, 0b00000001
	ldi r21, 0b00000010
	ldi r22, 0b00000100
	ldi r23, 0b00001000
	ldi r24, 0b00010000
	ldi r25, 0b00100000
	ldi r26, 0b01000000
	ldi r27, 0b10000000
	ldi r28, 0b11111111
	ldi r29, 0b00000000

;CONFIGURACION DE LOS PUERTO
	out DDRD, r28
	out DDRB, r28

	rjmp start

start:

	call delay_1000ms
	out PORTD, r20

	call delay_1000ms
	out PORTD, r21
	
	call delay_1000ms
	out PORTD, r22
	
	call delay_1000ms
	out PORTD, r23
	
	call delay_1000ms
	out PORTD, r24
	
	call delay_1000ms
	out PORTD, r25
	
	call delay_1000ms
	out PORTD, r26
	
	call delay_1000ms
	out PORTD, r27
	
	call delay_1000ms
	out PORTD, r29
	out PORTB, r20
	
	call delay_1000ms
	out PORTB, r21

	call reverso
	
	rjmp start
	
reverso:

	call delay_1000ms
	out PORTB,  r20
	
	call delay_1000ms
	out PORTB, r29
	out PORTD, r27
	
	call delay_1000ms
	out PORTD, r26
	
	call delay_1000ms
	out PORTD, r25
	
	call delay_1000ms
	out PORTD, r24
	
	call delay_1000ms
	out PORTD, r23
	
	call delay_1000ms
	out PORTD, r22
	
	call delay_1000ms
	out PORTD, r21
	
	ret

delay_1000ms: ;Función para retrasar

	ldi r18, 6  ;Debe ser 99

repetir2:

	ldi r16, 99

repetir1:

	ldi r17, 199

repetir:

	nop
	nop
	nop
	nop
	nop
	dec r17
	brne repetir ;2 ciclos
	dec r16
	brne repetir1
	dec r18
	brne repetir2
	
	ret