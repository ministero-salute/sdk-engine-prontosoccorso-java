/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;


@SpringBootApplication
@ComponentScan({"it.mds.sdk.flusso.emurps.controller", "it.mds.sdk.flusso.emurps", "it.mds.sdk.rest.persistence.entity",
        "it.mds.sdk.libreriaregole.validator",
        "it.mds.sdk.flusso.emurps.service", "it.mds.sdk.flusso.emurpsold.tracciato",
        "it.mds.sdk.gestoreesiti", "it.mds.sdk.flusso.emurpsold.parser.regole", "it.mds.sdk.flusso.emurpsold.parser.regole.conf",
        "it.mds.sdk.connettoremds"})

@OpenAPIDefinition(info = @Info(title = "SDK Ministero Della Salute - Flusso PS1", version = "0.0.5-SNAPSHOT", description = "Flusso EMUR-PS"))
public class FlussoPS {
	public static void main(String[] args) {
		SpringApplication.run(FlussoPS.class, args);
	}
}


