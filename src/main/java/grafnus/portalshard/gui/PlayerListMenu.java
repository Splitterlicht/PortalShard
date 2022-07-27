package grafnus.portalshard.gui;

import grafnus.portalshard.database.data.PortalData;
import me.lucko.helper.menu.Gui;
import org.bukkit.entity.Player;

public class PlayerListMenu extends Gui {

    private PortalData portal;

    public PlayerListMenu(Player player, PortalData data) {
        super(player, 6, "Select Player");
        this.portal = data;
    }

    @Override
    public void redraw() {

    }
}
