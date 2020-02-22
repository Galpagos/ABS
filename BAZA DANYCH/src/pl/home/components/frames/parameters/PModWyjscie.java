package pl.home.components.frames.parameters;

import java.util.List;

import Enums.SLRodzajeAbsencji;
import Frames.dbAccess.Components.ParametryWyjscia;
import lombok.Builder;
import lombok.Getter;

@Builder
public class PModWyjscie extends ParametryWyjscia {

	private @Getter List<SLRodzajeAbsencji> listaAbsencji;
}
