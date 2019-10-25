; Interruptor_Contador.asm
;
; Created: 17/10/2019 03:43:28 p. m.
; Author : emman
;
rjmp start

start:
	ldi r16, 0b00000000 ; registro para configurar puertos de entrada
	ldi r17, 0b00000000 ; registro para encender los leds el cual se incrementará 
	ldi r18, 0b11111111 ; registro para configurar puertos de salida
	ldi r20, 63			; registro para decrementar el ciclo
	
	out DDRB, r18 		; configura el puerto B como salida
	out DDRD, r16 		; configura el prt D como entrada
	nop
	
CICLO:
	in r21, PIND 		; lee entrada del puerto B
	sbis PIND, 7 		;Si el bit de la posicion 0 es 1 se salta un registro, sino continua normal
	rjmp BOTON_ON
	rjmp BOTON_OFF
	rjmp CICLO

BOTON_ON:
	out PORTB, r17		; Enciende el led
	
	brbs 0, CICLO 		; Si el bit C del Rr SERG = 1, regresa a ciclo
	
	dec r20				; controla las repeticiones 
	cpi r20, 0 			; compara que el r21 sea igual a 0
	breq start 			; si el r21  = 0 brinca a start
	
	sec 				;activar bandera de SERG en el bit C=1
	inc r17 			; incrementa r17
	rjmp CICLO

BOTON_OFF:
	out PORTB, r17			;Envia el código para apagar el LED
	clc						;vuelve 0 el bit C de Rr SERG
	rjmp CICLO
