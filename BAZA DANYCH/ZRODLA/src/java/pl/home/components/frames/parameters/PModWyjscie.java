package pl.home.components.frames.parameters;

import java.util.List;

import enums.SLRodzajeAbsencji;

import ProjektGlowny.commons.Frames.ParametryWyjscia;
import lombok.Builder;
import lombok.Getter;

@Builder
public class PModWyjscie extends ParametryWyjscia {

	private @Getter List<SLRodzajeAbsencji> mListaAbsencji;
}
