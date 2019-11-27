;
; Created: 30/09/2019 02:31:44 p. m.
; Author : Emmanuel
;


; Replace with your application code
.include<m328pdef.inc>


.def unidad = r20
.def decena = r21
.def centena = r22
.def decimal = r23
.def btn1 = r24
.def btn2 = r25




.org    0x000
rjmp    START

START:
	ldi r26, 0b00000000
	out PORTB, r26
    ldi        unidad, 0
    ldi        decena, 0
    ldi        centena, 0
	

                                ;DDRx: 1 = Salida, 0 = entrada
    ldi        r16,0b00100000        ;configura PB0 como entrada y PB5 (LED) como salida
    out        DDRB,r16

    ldi        r16,0b00000000        ;configura puertoC como entrada (ADC)
    out        DDRC,r16

    nop                            ;retardo
                                ;PORTx: 1 = HIGH, 0 = LOW (siempre y cuando el pin se configuro como salida)    
    call    UART_CONFIG

    ;call    ADC_CONFIG
   

CICLO:
;--------Envia datos del potenciometro-----

	call adcInit2
    call adcRead
    call adcWait
	lds r18, ADCL
    lds r19, ADCH


    ldi unidad, 0
    ldi decena, 0
    ldi centena, 0
	ldi decimal, 48

    mov r16, r18        ; r16 = r18

	call    ASCII
    
    ldi        r17, 48
    add        unidad, r17
    add        decena, r17
    add        centena, r17    

    mov        r16, centena            
    call    UART_TRANSMIT

    mov        r16, decena            
    call    UART_TRANSMIT

    mov        r16, unidad            
    call    UART_TRANSMIT

	ldi r16, 124
	call UART_TRANSMIT

	;---------Envia los valores de temperatura 8bits--------------

	call adcInit
    call adcRead
    call adcWait
	lds r18, ADCL
    lds r19, ADCH


    ldi unidad, 0
    ldi decena, 0
    ldi centena, 0
	ldi decimal, 48

    mov r16, r18        
	call DIVIDIR
    call    ASCII
    
    ldi        r17, 48
    add        unidad, r17
    add        decena, r17
    add        centena, r17    

    mov        r16, centena            
    call    UART_TRANSMIT

    mov        r16, decena            
    call    UART_TRANSMIT

    mov        r16, unidad            
    call    UART_TRANSMIT

	ldi r16, 46
	call UART_TRANSMIT

	mov r16, decimal
	call UART_TRANSMIT

	ldi r16, 124
	call UART_TRANSMIT
	
	;--------Envia los 2 bits faltantes---------

	ldi unidad, 0
    ldi decena, 0
    ldi centena, 0
	ldi decimal, 48

    mov r16, r19        
	call DIVIDIR
    call    ASCII
    
    ldi        r17, 48
    add        unidad, r17
    add        decena, r17
    add        centena, r17    

    mov        r16, centena            
    call    UART_TRANSMIT

    mov        r16, decena            
    call    UART_TRANSMIT

    mov        r16, unidad            
    call    UART_TRANSMIT

	ldi r16, 46
	call UART_TRANSMIT

	mov r16, decimal
	call UART_TRANSMIT

	ldi r16, 124
	call UART_TRANSMIT


	;---------Envió de datos del boton aceptar-----------
	call BOTON1
	ldi unidad, 0
    ldi decena, 0
    ldi centena, 0
	ldi decimal, 48

    mov r16, btn1       
    call    ASCII
    
    ldi        r17, 48
    add        unidad, r17  

    mov        r16, unidad            
    call    UART_TRANSMIT

	ldi r16, 124
	call UART_TRANSMIT

	;--------eNVIO DE DATOS DEL BOTON 2-------
	call BOTON2
	ldi unidad, 0
    ldi decena, 0
    ldi centena, 0
	ldi decimal, 48

    mov r16, btn2       
    call    ASCII
    
    ldi        r17, 48
    add        unidad, r17  

    mov        r16, unidad            
    call    UART_TRANSMIT

	ldi r16, 124
	call UART_TRANSMIT

	;FIN ENvio de datos
    ldi        r16, 10            ;Salto de linea
    call    UART_TRANSMIT
	call delay_100ms 
    ;--retardo de 1 segundo----------                               
    

    rjmp CICLO
;--------convertir a ASCII------------------
;-----r16 guarda el valor en decimal--------    
ASCII:
L_CENTENA:
    cpi        r16, 100        ;Rd, Rr
    BRLO    L_DECENA        ; brinca si r16 < 100
    inc        centena
    subi    r16, 100
    rjmp    L_CENTENA
L_DECENA:
    cpi        r16, 10            ;Rd, Rr
    BRLO    L_UNIDAD        ; brinca si r16 < 100
    inc        decena
    subi    r16, 10
    rjmp    L_DECENA
L_UNIDAD:
    cpi        r16, 1            ;Rd, Rr
    BRLO    L_FIN        ; brinca si r16 < 100
    inc        unidad
    subi    r16, 1
    rjmp    L_UNIDAD
L_FIN:
    ret


;-----rutina para configurar USART---------
UART_CONFIG:
    ldi        r16, 0b00000110        ;modo asincrono, paridad deshabilitada,8 bits de datos
    sts        UCSR0C,r16
    
    ;------configura baud rate----------------------
    ldi        r17, 0b00000000
    ldi        r16, 0b00001000        ;115200 baudios

    sts        UBRR0H, r17
    sts        UBRR0L, r16

    ldi        r16, 0b00011000        ;RxEn = 1 TxEn = 1  habilita pines Rx y Tx
    sts        UCSR0B, r16
    ret
UART_TRANSMIT:    
    LDS        r17, UCSR0A            

    bst        r17, 5            ;carga el valor a la bandera T
    BRTS    UART_FREE        ;brinca si T=1
    rjmp    UART_TRANSMIT
UART_FREE:
    sts        UDR0,r16        ;envia el dato guardado en r16
    ret


;---------------------------------Confiigurar ADC
adcInit:
    ldi r16, 0b01000000   ; Voltage Reference: AVcc with external capacitor at AREF pin
    sts ADMUX, r16        ; Enable ADC Right Adjust Result
                          ; Analog Channel: ADC0

    ldi r16, 0b10000111   ; Enable ADC
    sts ADCSRA, r16       ; ADC Prescaling Factor: 128

    ret

adcInit2:
    ldi r16, 0b01000001   ; Voltage Reference: AVcc with external capacitor at AREF pin
    sts ADMUX, r16        ; Enable ADC Right Adjust Result
                          ; Analog Channel: ADC1

    ldi r16, 0b10000111   ; Enable ADC
    sts ADCSRA, r16       ; ADC Prescaling Factor: 128

    ret

adcRead:
    ldi r16, 0b01000000   ; Set ADSC flag to Trigger ADC Conversion process
    lds r17, ADCSRA       ;
    or  r17, r16          ;
    sts  ADCSRA, r17      ;
    ret

adcWait:
    lds r17, ADCSRA       ; Observe the ADIF flag, it gets set by hardware when ADC conversion completes
    sbrs r17, 4           ;

    jmp adcWait           ; Keep checking until the flag is set by hardware

    ldi r16, 0b00010000   ; Set the flag again to signal 'ready-to-be-cleared' by hardware
    lds r17, ADCSRA       ;
    or  r17, r16          ;
    sts  ADCSRA, r17      ;
    ret


;------Funcion Dividir entre 2---------------
DIVIDIR:
	lsr r16
	brcs DECIMAL_1
	nop
ret

DECIMAL_1:
	ldi decimal, 53
ret

;-------Funcion Boton 1------------
BOTON1:
	ldi btn1, 0b00000000
	sbic PINB, 0
	ldi btn1, 0b00000001
	nop
	nop
	ret
;------funcion Boton2--------------
	BOTON2:
	ldi btn2, 0b00000000
	sbic PINB, 5
	ldi btn2, 0b00000001
	nop
	NOP
	ret


;--------rutina de retardo------------
delay_10ms:
    ldi    r17,99       ; 1 ciclo
repetir1:
    ldi    r18,199      ; 1 ciclo
repetir:
    nop                    ; 1 ciclo
    nop                    ; 1 ciclo
    nop                    ; 1 ciclo
    nop                    ; 1 ciclo
    nop                    ; 1 ciclo
    dec    r18            ; 1 ciclo
    brne repetir        ; 2 ciclos
    dec    r17            ; 1 ciclo
    brne repetir1        ; 2 ciclos

    ret                    ; 5 ciclo  

 delay_500ms:
  call delay_100ms
  call delay_100ms
  call delay_100ms
  call delay_100ms
  call delay_100ms
  ret
;----------------------Esta es la subrutina de retardo delay_100ms  -------------------------

      
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